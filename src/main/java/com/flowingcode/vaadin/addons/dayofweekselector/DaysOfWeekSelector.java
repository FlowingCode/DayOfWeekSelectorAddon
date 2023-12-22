package com.flowingcode.vaadin.addons.dayofweekselector;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("serial")
@CssImport("./styles/fc-days-of-week-selector-styles.css")
public class DaysOfWeekSelector extends CustomField<Set<DayOfWeek>> {

  private static final String BUTTON_SIZE = "40px";

  private static class DayOfWeekButton extends Button {
    private static final String CLASS_NAME = "fc-days-of-week-selector-button";

    private final DayOfWeek dayOfWeek;
    private boolean state;

    public DayOfWeekButton(DayOfWeek dayOfWeek, String text) {
      super(text);
      this.dayOfWeek = dayOfWeek;

      setClassName(CLASS_NAME);
      addThemeVariants(ButtonVariant.LUMO_ICON);
      setWidth(BUTTON_SIZE);
      setMaxWidth(BUTTON_SIZE);
      setHeight(BUTTON_SIZE);
      setMaxHeight(BUTTON_SIZE);
      getStyle().set("--lumo-button-size", BUTTON_SIZE);
      getStyle().set("border-radius", "50%");

      addClickListener(e -> toggleState());
    }

    public DayOfWeek getDayOfWeek() {
      return dayOfWeek;
    }

    private void toggleState() {
      setState(!state);
    }

    private boolean getState() {
      return state;
    }

    private void setState(boolean state) {
      this.state = state;
      getThemeNames().set(ButtonVariant.LUMO_PRIMARY.getVariantName(), state);
    }

  }

  private HorizontalLayout buttonsLayout;

  public DaysOfWeekSelector() {
    getStyle().set("padding", "var(--lumo-space-m)");

    buttonsLayout = new HorizontalLayout();
    buttonsLayout.add(new DayOfWeekButton(DayOfWeek.SUNDAY, "S"));
    buttonsLayout.add(new DayOfWeekButton(DayOfWeek.MONDAY, "M"));
    buttonsLayout.add(new DayOfWeekButton(DayOfWeek.TUESDAY, "T"));
    buttonsLayout.add(new DayOfWeekButton(DayOfWeek.WEDNESDAY, "W"));
    buttonsLayout.add(new DayOfWeekButton(DayOfWeek.THURSDAY, "T"));
    buttonsLayout.add(new DayOfWeekButton(DayOfWeek.FRIDAY, "F"));
    buttonsLayout.add(new DayOfWeekButton(DayOfWeek.SATURDAY, "S"));

    getButtons().forEach(button -> button.addClickListener(ev -> updateValue()));
    add(buttonsLayout);
    clear();
  }

  @Override
  public Set<DayOfWeek> getEmptyValue() {
    return EnumSet.noneOf(DayOfWeek.class);
  }

  @Override
  protected boolean valueEquals(Set<DayOfWeek> value1, Set<DayOfWeek> value2) {
    return value1 != value2 && Objects.equals(value1, value2);
  }

  private Stream<DayOfWeekButton> getButtons() {
    return buttonsLayout.getChildren()
        .filter(DayOfWeekButton.class::isInstance)
        .map(DayOfWeekButton.class::cast);
  }

  @Override
  protected Set<DayOfWeek> generateModelValue() {
    return getButtons().filter(DayOfWeekButton::getState).map(DayOfWeekButton::getDayOfWeek)
        .collect(Collectors.toCollection(this::getEmptyValue));
  }

  @Override
  protected void setPresentationValue(Set<DayOfWeek> newPresentationValue) {
    getButtons()
        .forEach(button -> button.setState(newPresentationValue.contains(button.getDayOfWeek())));
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    getElement().setProperty("readonly", readOnly);
    getElement().setAttribute("readonly", readOnly);
    getButtons().forEach(button -> button.setEnabled(!readOnly));
  }

}