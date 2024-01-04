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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;

@DemoSource
@PageTitle("Demo")
@SuppressWarnings("serial")
@Route(value = "day-of-week-selector/demo", layout = DayOfWeekSelectorDemoView.class)
public class DayOfWeekSelectorDemo extends Div {

  public DayOfWeekSelectorDemo() {
    DayOfWeekSelector selector1 =
        new DayOfWeekSelector("Enabled", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    add(selector1);

    DayOfWeekSelector selector2 =
        new DayOfWeekSelector("Read-only", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selector2.setReadOnly(true);
    add(selector2);

    DayOfWeekSelector selector3 =
        new DayOfWeekSelector("Disabled", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selector3.setEnabled(false);
    add(selector3);
  }
}
