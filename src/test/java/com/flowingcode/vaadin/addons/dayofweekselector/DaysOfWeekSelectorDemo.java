package com.flowingcode.vaadin.addons.dayofweekselector;

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@DemoSource
@PageTitle("DaysOfWeekSelector Add-on Demo")
@SuppressWarnings("serial")
@Route(value = "day-of-week-selector", layout = DaysOfWeekSelectorDemoView.class)
public class DaysOfWeekSelectorDemo extends Div {

  public DaysOfWeekSelectorDemo() {
    add(new DaysOfWeekSelector());
  }
}
