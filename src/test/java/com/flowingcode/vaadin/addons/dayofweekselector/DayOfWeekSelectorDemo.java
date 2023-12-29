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
