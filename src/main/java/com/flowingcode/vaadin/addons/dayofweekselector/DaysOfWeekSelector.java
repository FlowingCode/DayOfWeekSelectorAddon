package com.flowingcode.vaadin.addons.dayofweekselector;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class DaysOfWeekSelector extends VerticalLayout
    implements HasValue<ValueChangeEvent<Set<DayOfWeek>>, Set<DayOfWeek>> {

  static final String BUTTON_SIZE = "40px";

  Span title = new Span("");
  Span error = new Span("");

  Map<DayOfWeek, Button> daysOfWeekButtons = new LinkedHashMap<>();
  Map<Button, Boolean> daySelected = new LinkedHashMap<>();

  public DaysOfWeekSelector() {
    setPadding(false);
    setSpacing(false);

    Component daysOfWeekButtons = configDaysOfWeekButtons();

    title.getStyle().set("font-size", "var(--lumo-font-size-s)");
    title.getStyle().setColor("var(--lumo-secondary-text-color)");
    title.getStyle().set("font-weight", "500");

    error.getStyle().set("font-size", "var(--lumo-font-size-xs)");
    error.getStyle().setColor("var(--lumo-error-text-color)");
    error.getStyle().set("line-height", "var(--lumo-line-height-xs)");

    add(title, daysOfWeekButtons, error);
    setAlignSelf(Alignment.CENTER, daysOfWeekButtons);

    getStyle().set("padding-top", "var(--lumo-space-m)");
  }

  private Component configDaysOfWeekButtons() {
    HorizontalLayout buttonsLayout = new HorizontalLayout();

    daysOfWeekButtons.put(DayOfWeek.SUNDAY, new Button("S"));
    daysOfWeekButtons.put(DayOfWeek.MONDAY, new Button("M"));
    daysOfWeekButtons.put(DayOfWeek.TUESDAY, new Button("T"));
    daysOfWeekButtons.put(DayOfWeek.WEDNESDAY, new Button("W"));
    daysOfWeekButtons.put(DayOfWeek.THURSDAY, new Button("T"));
    daysOfWeekButtons.put(DayOfWeek.FRIDAY, new Button("F"));
    daysOfWeekButtons.put(DayOfWeek.SATURDAY, new Button("S"));

    setEmpty();

    daysOfWeekButtons.forEach((d, b) -> {
      b.addThemeVariants(ButtonVariant.LUMO_ICON);
      b.setWidth(BUTTON_SIZE);
      b.setMaxWidth(BUTTON_SIZE);
      b.setHeight(BUTTON_SIZE);
      b.setMaxHeight(BUTTON_SIZE);
      b.getStyle().set("--lumo-button-size", BUTTON_SIZE);
      b.getStyle().set("border-radius", "50%");

      b.addClickListener(e -> toggleButtonState(b));

      buttonsLayout.add(b);
    });

    return buttonsLayout;
  }

  private void toggleButtonState(Button button) {
    setButtonState(button, !daySelected.get(button));
  }

  private void setButtonState(Button button, boolean state) {
    daySelected.put(button, state);

    if (state) {
      button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    } else {
      button.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }
  }

  public void setEmpty() {
    daysOfWeekButtons.forEach((d, b) -> {
      setButtonState(b, false);
    });
  }


  @Override
  public void setValue(Set<DayOfWeek> value) {
    setEmpty();
    for (DayOfWeek day : value) {
      setButtonState(daysOfWeekButtons.get(day), true);
    }
  }

  @Override
  public Set<DayOfWeek> getValue() {
    Set<DayOfWeek> days = new HashSet<>();

    daysOfWeekButtons.forEach((d, b) -> {
      if (daySelected.get(b)) {
        days.add(d);
      }
    });

    return days;
  }

  @Override
  public Registration addValueChangeListener(
      ValueChangeListener<? super ValueChangeEvent<Set<DayOfWeek>>> listener) {
    // TODO Auto-generated method stub
    return null;

  }

  @Override
  public void setReadOnly(boolean readOnly) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isReadOnly() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isRequiredIndicatorVisible() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setLabel(String label) {
    title.removeAll();

    if (label == null || label.isBlank()) {
      title.setVisible(false);
    } else {
      title.add(label);
    }
  }

  public Span getError() {
    return error;
  }

}