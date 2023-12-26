package com.flowingcode.vaadin.addons.dayofweekselector;

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.flowingcode.vaadin.addons.demo.SourceCodeViewer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;
import java.util.Set;
import java.util.stream.Collectors;

@DemoSource
@PageTitle("Binder")
@SuppressWarnings("serial")
@Route(value = "day-of-week-selector/binder", layout = DaysOfWeekSelectorDemoView.class)
public class DaysOfWeekSelectorBinderDemo extends Div {

  public DaysOfWeekSelectorBinderDemo() {

    // begin-block selector1
    Span span1 = new Span();
    Binder<Bean> binder1 = new Binder<>();
    DaysOfWeekSelector selector1 = new DaysOfWeekSelector("Bound field");
    binder1.forField(selector1).bind(Bean::getDays, Bean::setDays);
    add(selector1, span1);

    selector1.addValueChangeListener(ev -> {
      span1.setText(ev.getValue().stream().map(Object::toString).collect(Collectors.joining(", ")));
    });

    SourceCodeViewer.highlightOnHover(selector1, "selector1");
    // end-block

    // begin-block selector2
    Span span2 = new Span();
    Binder<Bean> binder2 = new Binder<>();
    DaysOfWeekSelector selector2 = new DaysOfWeekSelector("Bound field, required");
    binder2.forField(selector2).asRequired().bind(Bean::getDays, Bean::setDays);
    add(selector2, span2);

    selector2.addValueChangeListener(ev -> {
      span2.setText(ev.getValue().stream().map(Object::toString).collect(Collectors.joining(", ")));
    });

    SourceCodeViewer.highlightOnHover(selector2, "selector2");
    // end-block


  }

  private static class Bean {
    Set<DayOfWeek> days;

    public Set<DayOfWeek> getDays() {
      return days;
    }

    public void setDays(Set<DayOfWeek> days) {
      this.days = days;
    }
  }

}
