/*-
 * #%L
 * Day of Week Selector Add-on
 * %%
 * Copyright (C) 2023 - 2026 Flowing Code
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import {ResizeMixin} from '@vaadin/component-base/src/resize-mixin.js';
import '@vaadin/context-menu';
import type {ContextMenu, ContextMenuItem} from '@vaadin/context-menu';
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import {css, html, LitElement} from 'lit';
import {customElement, property, query, queryAssignedNodes} from 'lit/decorators.js';
import {Button} from "@vaadin/button";

@customElement('fc-days-of-week-selector')
export class DaysOfWeekSelector extends ResizeMixin(ThemableMixin(LitElement)) {

    @query('[part~="overflow-badge"]')
    _overflowBadge!: HTMLElement

    @queryAssignedNodes({slot: 'daysOfWeek', flatten: true})
    _daysOfWeek!: Array<Node>;

    @query('[part~="container"]')
    _container!: HTMLElement;

    @property()
    theme: string = '';

    @property()
    label: string = '';

    /**
     * Overflow context menu. It is kept in the light DOM (slotted into the shadow root)
     * so that the cloned buttons it renders share the same scope as the original ones
     * and get the same global theme styles (e.g. Aura).
     */
    private _overflowMenu?: ContextMenu;

    static styles = [
        css`
            :host {
                display: block;
                width: 100%;
            }

            [hidden] {
                display: none;
            }

            [part="container"] {
                position: relative;
                display: flex;
                width: 100%;
                flex-wrap: nowrap;
                overflow: hidden;
                align-items: center;
                gap: var(--fc-days-of-week-selector-button-space, var(--lumo-space-m, var(--vaadin-gap-m, 0.5rem)));
            }

            [part="overflow-badge"] {
                line-height: 0.5em;
                margin-left: calc(var(--lumo-space-s, var(--vaadin-gap-s, 8px)) * -1);
            }

            :host(:not([has-label])) [part='label'] {
                display: none;
            }

            [part="label"] {
                align-self: flex-start;
                color: var(--badge-list-label-color);
                font-weight: var(--badge-list-label-font-weight);
                font-size: var(--badge-list-label-font-size);
                margin-left: var(--badge-list-label-margin-left);
                transition: color 0.2s;
                line-height: inherit;
                padding-right: 1em;
                padding-bottom: 0.5em;
                padding-top: 0.25em;
                margin-top: -0.25em;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
                position: relative;
                max-width: 100%;
                box-sizing: border-box;
            }

            ::slotted([slot="overflowIcon"]) {
                --lumo-icon-size-m: var(--fc-days-of-week-selector-overflow-icon-size, 1em);             
            }
            
            /* Style the fallback <vaadin-icon> inside the slot */
            vaadin-icon.overflow-icon {
                --lumo-icon-size-m: var(--fc-days-of-week-selector-overflow-icon-size, 0.75em);
            }
         
        `
    ];

    static get is() {
        return 'fc-days-of-week-selector';
    }

    _set_theme(theme: string) {
        this.theme = theme;
    }

    /**
     * Override getter from `ResizeMixin` to observe parent.
     *
     * @protected
     * @override
     */
    get _observeParent() {
        return true;
    }

    /**
     * Implement callback from `ResizeMixin` to update badges
     * and detect whether to show or hide the overflow badge.
     *
     * @protected
     * @override
     */
    _onResize() {
        this._updateOnOverflow();
    }

    /**
     * Returns overflow button width.
     *
     * @private
     */
    private _getOverflowButtonWidth() {
        let overflowWidth = 0;
        const badge = this._overflowBadge;
        if (badge) {
            const wasHidden = badge.hasAttribute('hidden');
            if (wasHidden) {
                badge.removeAttribute('hidden');
            }
            overflowWidth = badge.getBoundingClientRect().width || 0;
            if (wasHidden) {
                badge.setAttribute('hidden', '');
            }
        }
        return overflowWidth;
    }

    private _updateOnOverflow() {
        const containerWidth = this._container.getBoundingClientRect().width;
        const containerGap = parseFloat(getComputedStyle(this._container).gap) || 0;

        let usedWidth = 0;
        const overflowButtons: Node[] = [];
        const overflowButtonWidth = this._getOverflowButtonWidth();

        for (const button of this._daysOfWeek) {
            button.style.display = '';
            // Add gap only for buttons after the first.
            const width = button.getBoundingClientRect().width + (usedWidth > 0 ? containerGap : 0);
            if (usedWidth + width > containerWidth - overflowButtonWidth) {
                const clonedButton = this._cloneButton(button);
                overflowButtons.push(clonedButton);

                // hide button from slot
                button.style.display = 'none';
            } else {
                usedWidth += width;
            }
        }

        // Update context menu items from hidden buttons
        const overflowItems: ContextMenuItem[] = overflowButtons.map(button => ({
            component: button as HTMLElement,
        }));
        if (this._overflowMenu) {
            this._overflowMenu.items = overflowItems;
        }

        // Update badges visibility
        this._overflowBadge.toggleAttribute('hidden', overflowItems.length < 1);
    }

    private _cloneButton(originalButton: Button) {
        const clonedButton = originalButton.cloneNode(true);
        clonedButton._originalButton = originalButton;
        clonedButton.removeAttribute("slot");
        clonedButton.style.display = '';
        clonedButton.addEventListener('click', () => {
            // Update server side button component state
            clonedButton._originalButton.$server.toggleState();
            // Toggle only the "primary" token, preserving any other theme tokens
            const themes = new Set((clonedButton.getAttribute('theme') || '').split(/\s+/).filter(Boolean));
            themes.has('primary') ? themes.delete('primary') : themes.add('primary');
            themes.size
                ? clonedButton.setAttribute('theme', [...themes].join(' '))
                : clonedButton.removeAttribute('theme');
            // Notify value change to DayOfWeekSelector component
            this.dispatchEvent(new CustomEvent('change', {bubbles: true}));
        });
        return clonedButton;
    }

    render() {
        return html`
            <div part="label">
                <label for="container">${this.label}</label>
            </div>
            <div part="container" class="container" id="container">
                <slot name="daysOfWeek"></slot>
                <vaadin-button part="overflow-badge" theme="${this.theme} tertiary-inline"
                               class="overflow-badge" hidden style="cursor: pointer">
                    <slot name="overflowIcon">
                        <!-- Fallback if no icon is provided -->
                        <vaadin-icon icon="vaadin:ellipsis-dots-v" class="overflow-icon"></vaadin-icon>
                    </slot>
                </vaadin-button>
            </div>
            <slot name="overflowMenu"></slot>
        `;
    }

    firstUpdated() {
        const menu = document.createElement('vaadin-context-menu') as ContextMenu;
        menu.slot = 'overflowMenu';
        menu.setAttribute('theme', 'fc-day-of-week-selector-context-menu');
        menu.openOn = 'click';
        menu.listenOn = this._overflowBadge;
        this.appendChild(menu);
        this._overflowMenu = menu;
    }

    update(_changedProperties: Map<PropertyKey, unknown>) {
        if (_changedProperties.has('label')) {
            this.toggleAttribute('has-label', this.label != null);
        }
        super.update(_changedProperties);
    }

}