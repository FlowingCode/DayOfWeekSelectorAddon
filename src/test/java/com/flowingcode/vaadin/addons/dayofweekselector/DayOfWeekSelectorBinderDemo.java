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
@Route(value = "day-of-week-selector/binder", layout = DayOfWeekSelectorDemoView.class)
public class DayOfWeekSelectorBinderDemo extends Div {

  public DayOfWeekSelectorBinderDemo() {

    // begin-block selector1
    Span span1 = new Span();
    Binder<Bean> binder1 = new Binder<>();
    DayOfWeekSelector selector1 = new DayOfWeekSelector("Bound field");
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
    DayOfWeekSelector selector2 = new DayOfWeekSelector("Bound field, required");
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
