package com.flowingcode.vaadin.addons.dayofweekselector;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("serial")
@CssImport("./styles/fc-days-of-week-selector-styles.css")
public class DayOfWeekSelector extends CustomField<Set<DayOfWeek>> {

  private static class DayOfWeekButton extends Button {
    private static final String CLASS_NAME = "fc-days-of-week-selector-button";

    private final DayOfWeek dayOfWeek;
    private boolean state;

    public DayOfWeekButton(DayOfWeek dayOfWeek, String text) {
      super(text);
      this.dayOfWeek = dayOfWeek;
      setClassName(CLASS_NAME);
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

  public DayOfWeekSelector() {
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

  public DayOfWeekSelector(DayOfWeek... value) {
    this();
    if (value.length > 0) {
      setValue(value[0], value);
    }
  }

  public DayOfWeekSelector(String label) {
    this();
    setLabel(label);
  }

  public DayOfWeekSelector(String label, DayOfWeek... value) {
    this(value);
    setLabel(label);
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

  public void setValue(DayOfWeek first, DayOfWeek... rest) {
    setValue(EnumSet.of(first, rest));
  }

  public void setI18N(DatePickerI18n i18n) {
    setWeekDaysShort(i18n.getWeekdaysShort());
    if (i18n.getFirstDayOfWeek() == 0) {
      setFirstDayOfWeek(DayOfWeek.SUNDAY);
    } else {
      setFirstDayOfWeek(DayOfWeek.of(i18n.getFirstDayOfWeek()));
    }
  }

  public void setWeekDaysShort(List<String> weekdaysShort) {
    Objects.requireNonNull(weekdaysShort);

    for (DayOfWeek day : DayOfWeek.values()) {
      int index = day.getValue() % 7;
      String text = weekdaysShort.get(index);
      getButtons().filter(button -> button.getDayOfWeek() == day)
          .forEach(button -> button.setText(text));
    }
  }

  public void setFirstDayOfWeek(DayOfWeek first) {
    DayOfWeekButton[] buttons = getButtons().toArray(DayOfWeekButton[]::new);
    if (buttons[0].dayOfWeek != first) {
      Arrays.sort(buttons, Comparator.comparing(DayOfWeekButton::getDayOfWeek));
      buttonsLayout.removeAll();
      for (int i = 0; i < 7; i++) {
        buttonsLayout.add(buttons[(i + first.getValue() - 1) % 7]);
      }
    }
  }

}