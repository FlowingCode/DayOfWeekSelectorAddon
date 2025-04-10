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
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.stream.Stream;

@DemoSource
@PageTitle("I18N")
@SuppressWarnings("serial")
@Route(value = "day-of-week-selector/i18n", layout = DayOfWeekSelectorDemoView.class)
public class DayOfWeekSelectorI18NDemo extends Div {

  public DayOfWeekSelectorI18NDemo() {
    DayOfWeekSelector selector = new DayOfWeekSelector();
    selector.setLabel("I18N with DatePickerI18n");

    DatePickerI18n i18n = new DatePickerI18n();
    i18n.setWeekdaysShort(Stream.of(DayOfWeek.values())
        .map(d -> d.getDisplayName(TextStyle.NARROW_STANDALONE, getLocale())).toList());
    i18n.setWeekdays(Stream.of(DayOfWeek.values())
        .map(d -> d.getDisplayName(TextStyle.FULL_STANDALONE, getLocale())).toList());
    i18n.setFirstDayOfWeek(DayOfWeek.MONDAY.getValue() - 1);
    selector.setI18N(i18n);
    add(selector);

    selector = new DayOfWeekSelector();
    selector.setLabel("I18N without DatePickerI18n");
    selector.setWeekDaysTooltip(
        List.of("Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"));
    selector.setWeekDaysShort(List.of("D", "L", "M", "X", "J", "V", "S"));
    selector.setFirstDayOfWeek(DayOfWeek.MONDAY);
    add(selector);
  }

}
