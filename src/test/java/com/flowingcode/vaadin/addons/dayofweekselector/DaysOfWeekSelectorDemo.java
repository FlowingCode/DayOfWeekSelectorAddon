package com.flowingcode.vaadin.addons.dayofweekselector;

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;

@DemoSource
@PageTitle("Demo")
@SuppressWarnings("serial")
@Route(value = "day-of-week-selector/demo", layout = DaysOfWeekSelectorDemoView.class)
public class DaysOfWeekSelectorDemo extends Div {

  public DaysOfWeekSelectorDemo() {
    DaysOfWeekSelector selector1 =
        new DaysOfWeekSelector("Enabled", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    add(selector1);

    DaysOfWeekSelector selector2 =
        new DaysOfWeekSelector("Read-only", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selector2.setReadOnly(true);
    add(selector2);

    DaysOfWeekSelector selector3 =
        new DaysOfWeekSelector("Disabled", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
    selector3.setEnabled(false);
    add(selector3);
  }
}
