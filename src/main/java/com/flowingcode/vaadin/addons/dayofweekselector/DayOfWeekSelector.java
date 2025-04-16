/*-
 * #%L
 * Day of Week Selector Add-on
 * %%
 * Copyright (C) 2023 - 2024 Flowing Code
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
package com.flowingcode.vaadin.addons.dayofweekselector;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.Element;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Shows the days of the week so they can be selected.
 */
@SuppressWarnings("serial")
@CssImport("./styles/fc-days-of-week-selector-styles.css")
@JsModule("./src/fc-days-of-week-selector.ts")
@Tag("fc-days-of-week-selector")
public class DayOfWeekSelector extends CustomField<Set<DayOfWeek>> {

  public static final class CssProperties {
    public static final String OVERFLOW_ICON_SIZE = "--fc-days-of-week-selector-overflow-icon-size";

    private CssProperties() {
    }
  }

  private static class DayOfWeekButton extends Button {
    private static final String CLASS_NAME = "fc-days-of-week-selector-button";

    private final DayOfWeek dayOfWeek;
    private boolean state;

    public DayOfWeekButton(DayOfWeek dayOfWeek, String text) {
      super(text);
      this.dayOfWeek = dayOfWeek;
      setClassName(CLASS_NAME);
      addClickListener(e -> toggleState());
    }

    public DayOfWeek getDayOfWeek() {
      return dayOfWeek;
    }

    @ClientCallable
    private void toggleState() {
      setState(!state);
    }

    private boolean getState() {
      return state;
    }

    private void setState(boolean state) {
      this.state = state;
      getThemeNames().set(ButtonVariant.LUMO_PRIMARY.getVariantName(), state);
    }

  }

  private List<DayOfWeekButton> dayButtons = new ArrayList<>();

  /**
   * Creates a new instance of {@code DayOfWeekSelector}.
   */
  public DayOfWeekSelector() {
    initDefaultDayButtons();
  }

  /**
   * Constructs a new instance of {@code DayOfWeekSelector} with the given value.
   *
   * @param value the initial value.
   */
  public DayOfWeekSelector(DayOfWeek... value) {
    this();
    if (value.length > 0) {
      setValue(value[0], value);
    }
  }

  /**
   * Constructs a new instance of {@code DayOfWeekSelector} with the given label.
   *
   * @param label the text to set as the label
   */
  public DayOfWeekSelector(String label) {
    this();
    setLabel(label);
  }

  /**
   * Constructs a new instance of {@code DayOfWeekSelector} with the given label and initial value.
   *
   * @param label the text to set as the label
   * @param value the initial value.
   */
  public DayOfWeekSelector(String label, DayOfWeek... value) {
    this(value);
    setLabel(label);
  }

  @Override
  public Set<DayOfWeek> getEmptyValue() {
    return EnumSet.noneOf(DayOfWeek.class);
  }

  @Override
  protected boolean valueEquals(Set<DayOfWeek> value1, Set<DayOfWeek> value2) {
    return value1 != value2 && Objects.equals(value1, value2);
  }

  @Override
  protected Set<DayOfWeek> generateModelValue() {
    return dayButtons.stream().filter(DayOfWeekButton::getState).map(DayOfWeekButton::getDayOfWeek)
      .collect(Collectors.toCollection(this::getEmptyValue));
  }

  @Override
  protected void setPresentationValue(Set<DayOfWeek> newPresentationValue) {
    dayButtons
      .forEach(button -> button.setState(newPresentationValue.contains(button.getDayOfWeek())));
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    getElement().setProperty("readonly", readOnly);
    getElement().setAttribute("readonly", readOnly);
    dayButtons.forEach(button -> {
      button.setEnabled(!readOnly);
      if (readOnly) {
        button.addClassName("readOnly");
      } else {
        button.removeClassName("readOnly");
      }
    });
  }

  /**
   * Sets the value of this object.
   */
  public void setValue(DayOfWeek first, DayOfWeek... rest) {
    setValue(EnumSet.of(first, rest));
  }

  /**
   * Sets the internationalization properties for this component.
   *
   * @param i18n the internationalized properties, not <code>null</code>
   */
  public void setI18N(DatePickerI18n i18n) {
    if (i18n.getWeekdaysShort() != null) {
      setWeekDaysShort(i18n.getWeekdaysShort());
    }
    if (i18n.getWeekdays() != null) {
      setWeekDaysTooltip(i18n.getWeekdays());
    }
    if (i18n.getFirstDayOfWeek() == 0) {
      setFirstDayOfWeek(DayOfWeek.SUNDAY);
    } else {
      setFirstDayOfWeek(DayOfWeek.of(i18n.getFirstDayOfWeek()));
    }
  }

  /**
   * Sets the short names of the week days, starting from {@code sun} and ending on {@code sat}.
   *
   * @param weekdaysShort the short names of the week days
   */
  public void setWeekDaysShort(List<String> weekdaysShort) {
    Objects.requireNonNull(weekdaysShort);

    for (DayOfWeek day : DayOfWeek.values()) {
      int index = day.getValue() % 7;
      String text = weekdaysShort.get(index);
      dayButtons.stream().filter(button -> button.getDayOfWeek() == day)
        .forEach(button -> button.setText(text));
    }
  }

  /**
   * Sets the tooltips of the week days, starting from {@code sun} and ending on {@code sat}.
   *
   * @param weekdaysTooltip the tooltips of the week days
   */
  public void setWeekDaysTooltip(List<String> weekdaysTooltip) {
    Objects.requireNonNull(weekdaysTooltip);

    for (DayOfWeek day : DayOfWeek.values()) {
      int index = day.getValue() % 7;
      String text = weekdaysTooltip.get(index);
      dayButtons.stream().filter(button -> button.getDayOfWeek() == day)
        .forEach(button -> button.setTooltipText(text));
    }
  }

  /**
   * Sets the first day of the week.
   * <p>
   * 0 for Sunday, 1 for Monday, 2 for Tuesday, 3 for Wednesday, 4 for Thursday, 5 for Friday, 6 for
   * Saturday.
   *
   * @param first the index of the first day of the week
   * @throws IllegalArgumentException if firstDayOfWeek is invalid
   */
  public void setFirstDayOfWeek(DayOfWeek first) {
    DayOfWeekButton[] buttons = dayButtons.toArray(DayOfWeekButton[]::new);
    if (buttons[0].dayOfWeek != first) {
      var sortedButtons = new ArrayList<DayOfWeekButton>();
      Arrays.sort(buttons, Comparator.comparing(DayOfWeekButton::getDayOfWeek));
      for (int i = 0; i < buttons.length; i++) {
        sortedButtons.add(buttons[(i + first.getValue() - 1) % buttons.length]);
      }
      setDayButtons(sortedButtons);
    }
  }

  /**
   * Sets the icon for the overflow menu trigger.
   *
   * @param icon the icon component, not {@code null}
   * @throws NullPointerException if {@code icon} is {@code null}
   */
  public void setOverflowIcon(Component icon) {
    Objects.requireNonNull(icon, "icon cannot be null");

    // Remove any existing slotted icon before appending the new one
    getElement().getChildren()
      .filter(el -> "overflowIcon".equals(el.getAttribute("slot")))
      .findFirst()
      .ifPresent(Element::removeFromParent);

    icon.setClassName("overflow-icon");
    // Assign icon to the slot
    icon.getElement().setAttribute("slot", "overflowIcon");
    getElement().appendChild(icon.getElement());
  }

  private void initDefaultDayButtons() {
    setDayButtons(List.of(new DayOfWeekButton(DayOfWeek.SUNDAY, "S"),
      new DayOfWeekButton(DayOfWeek.MONDAY, "M"),
      new DayOfWeekButton(DayOfWeek.TUESDAY, "T"),
      new DayOfWeekButton(DayOfWeek.WEDNESDAY, "W"),
      new DayOfWeekButton(DayOfWeek.THURSDAY, "T"),
      new DayOfWeekButton(DayOfWeek.FRIDAY, "F"),
      new DayOfWeekButton(DayOfWeek.SATURDAY, "S")
    ));
  }

  private void setDayButtons(List<DayOfWeekButton> buttons) {
    this.clearDayButtons();
    this.dayButtons = buttons;
    buttons.forEach(button -> button.addClickListener(ev -> updateValue()));
    this.addDayButtons();
  }

  private void addDayButtons() {
    this.dayButtons.forEach(button -> {
      button.getElement().setAttribute("slot", "daysOfWeek");
      this.getElement().appendChild(button.getElement());
    });
  }

  private void clearDayButtons() {
    this.dayButtons.forEach(button -> {
      button.getElement().removeAttribute("slot");
      button.removeFromParent();
    });
  }

}
