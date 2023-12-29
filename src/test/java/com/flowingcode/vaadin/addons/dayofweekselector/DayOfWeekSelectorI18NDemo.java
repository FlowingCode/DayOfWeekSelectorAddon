package com.flowingcode.vaadin.addons.dayofweekselector;

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;
import java.util.List;

@DemoSource
@PageTitle("I18N")
@SuppressWarnings("serial")
@Route(value = "day-of-week-selector/i18n", layout = DayOfWeekSelectorDemoView.class)
public class DayOfWeekSelectorI18NDemo extends Div {

  public DayOfWeekSelectorI18NDemo() {
    DayOfWeekSelector selector = new DayOfWeekSelector();
    selector.setWeekDaysShort(List.of("D", "L", "M", "X", "J", "V", "S"));
    selector.setFirstDayOfWeek(DayOfWeek.MONDAY);
    add(selector);
  }

}
