package com.example.demo.controller.rdto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StockShoeRDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-11-24T14:52:49.294969600+01:00[Europe/Madrid]")
public class StockShoeRDTO   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("size")
  private Integer size = null;

  @JsonProperty("color")
  private String color = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  public StockShoeRDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of shoe
   * @return name
  **/
  @ApiModelProperty(value = "Name of shoe")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StockShoeRDTO size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Size of shoe
   * @return size
  **/
  @ApiModelProperty(value = "Size of shoe")
  
    public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public StockShoeRDTO color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Color of shoe
   * @return color
  **/
  @ApiModelProperty(value = "Color of shoe")
  
    public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public StockShoeRDTO quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Quantity of shoes
   * @return quantity
  **/
  @ApiModelProperty(value = "Quantity of shoes")
  
    public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockShoeRDTO stockShoe = (StockShoeRDTO) o;
    return Objects.equals(this.name, stockShoe.name) &&
        Objects.equals(this.size, stockShoe.size) &&
        Objects.equals(this.color, stockShoe.color) &&
        Objects.equals(this.quantity, stockShoe.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, size, color, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StockShoeRDTO {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
