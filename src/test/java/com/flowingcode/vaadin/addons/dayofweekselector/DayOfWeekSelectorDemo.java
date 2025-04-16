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

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@DemoSource
@SuppressWarnings("serial")
@PageTitle("Demo")
@Route(value = "day-of-week-selector/demo", layout = DayOfWeekSelectorDemoView.class)
public class DayOfWeekSelectorDemo extends VerticalLayout {

  public DayOfWeekSelectorDemo() {
    addClassName("demo");

    DayOfWeekSelector selector1 = new DayOfWeekSelector("Enabled", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selector1.setWidthFull();
    Button updateValueButton = new Button("Set value to SUN-MON-SAT");
    updateValueButton.addClickListener(e -> {
      selector1.setValue(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    });
    VerticalLayout content=new VerticalLayout(selector1, updateValueButton);
    content.setPadding(false);
    add(content);

    DayOfWeekSelector selectorOverflow = new DayOfWeekSelector("Overflow", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selectorOverflow.setWidth("200px");
    selectorOverflow.setOverflowIcon(LumoIcon.ANGLE_RIGHT.create());
    selectorOverflow.getStyle().set(DayOfWeekSelector.CssProperties.OVERFLOW_ICON_SIZE, "1em");

    Div valueContent = new Div();
    Consumer<Set<DayOfWeek>> valueRenderer =
      value -> valueContent.setText("Value: " + value.stream().map(Enum::name).collect(Collectors.joining(" - ")));
    add(new Div(selectorOverflow, valueContent));
    valueRenderer.accept(selectorOverflow.getValue());
    selectorOverflow.addValueChangeListener(e -> valueRenderer.accept(e.getValue()));

    DayOfWeekSelector selector2 = new DayOfWeekSelector("Read-only", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selector2.setReadOnly(true);
    add(selector2);

    DayOfWeekSelector selector3 = new DayOfWeekSelector("Disabled", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selector3.setEnabled(false);
    add(selector3);
  }
}
