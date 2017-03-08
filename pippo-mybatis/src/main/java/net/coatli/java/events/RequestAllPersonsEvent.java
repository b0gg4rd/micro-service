package net.coatli.java.events;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RequestAllPersonsEvent {

  private String name;

  public RequestAllPersonsEvent() {
  }

  public String getName() {
    return name;
  }

  public RequestAllPersonsEvent setName(final String name) {
    this.name = name;

    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("name", name)
        .toString();
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof RequestAllPersonsEvent)) {
      return false;
    }
    final RequestAllPersonsEvent castOther = (RequestAllPersonsEvent) other;
    return new EqualsBuilder().append(name, castOther.name).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(name).toHashCode();
  }

}
