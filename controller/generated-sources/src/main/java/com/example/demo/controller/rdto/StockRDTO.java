package com.example.demo.controller.rdto;

import java.util.Objects;
import com.example.demo.controller.rdto.StockShoeRDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StockRDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-11-24T14:52:49.294969600+01:00[Europe/Madrid]")
public class StockRDTO   {
  /**
   * Stock status EMPTY - 0 SOME -  more than 0 FULL - 30
   */
  public enum StateEnum {
    EMPTY("EMPTY"),
    
    SOME("SOME"),
    
    FULL("FULL");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StateEnum fromValue(String text) {
      for (StateEnum b : StateEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("state")
  private StateEnum state = null;

  @JsonProperty("shoes")
  @Valid
  private List<StockShoeRDTO> shoes = null;

  public StockRDTO state(StateEnum state) {
    this.state = state;
    return this;
  }

  /**
   * Stock status EMPTY - 0 SOME -  more than 0 FULL - 30
   * @return state
  **/
  @ApiModelProperty(readOnly = true, value = "Stock status EMPTY - 0 SOME -  more than 0 FULL - 30")
  
    public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }

  public StockRDTO shoes(List<StockShoeRDTO> shoes) {
    this.shoes = shoes;
    return this;
  }

  public StockRDTO addShoesItem(StockShoeRDTO shoesItem) {
    if (this.shoes == null) {
      this.shoes = new ArrayList<StockShoeRDTO>();
    }
    this.shoes.add(shoesItem);
    return this;
  }

  /**
   * Get shoes
   * @return shoes
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<StockShoeRDTO> getShoes() {
    return shoes;
  }

  public void setShoes(List<StockShoeRDTO> shoes) {
    this.shoes = shoes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockRDTO stock = (StockRDTO) o;
    return Objects.equals(this.state, stock.state) &&
        Objects.equals(this.shoes, stock.shoes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state, shoes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StockRDTO {\n");
    
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    shoes: ").append(toIndentedString(shoes)).append("\n");
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
