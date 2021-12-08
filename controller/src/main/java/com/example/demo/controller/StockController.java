package com.example.demo.controller;

import com.example.demo.dto.out.Stock;
import com.example.demo.facade.StockFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockFacade stockFacade;

    @Operation(summary = "Get the stock")
    @ApiResponse(responseCode = "200", description = "Found the stock",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Stock.class),
                    examples = {@ExampleObject(name = "A Stock with some shoes",
                            value = "{\n" +
                                    "\n" +
                                    " \"state\": \"SOME\",\n" +
                                    "\n" +
                                    " \"shoes\": [\n" +
                                    "\n" +
                                    "   {\n" +
                                    "\n" +
                                    "     \"color\": \"BLACK\",\n" +
                                    "\n" +
                                    "     \"size\": 40,\n" +
                                    "\n" +
                                    "     \"quantity\": 10\n" +
                                    "\n" +
                                    "   },\n" +
                                    "\n" +
                                    "   {\n" +
                                    "\n" +
                                    "     \"color\": \"BLACK\",\n" +
                                    "\n" +
                                    "     \"size\": 41,\n" +
                                    "\n" +
                                    "     \"quantity\": 0\n" +
                                    "\n" +
                                    "   },\n" +
                                    "\n" +
                                    "   {\n" +
                                    "\n" +
                                    "     \"color\": \"BLUE\",\n" +
                                    "\n" +
                                    "     \"size\": 39,\n" +
                                    "\n" +
                                    "     \"quantity\": 10\n" +
                                    "\n" +
                                    "   }\n" +
                                    "\n" +
                                    " ]\n" +
                                    "\n" +
                                    "}"),
                            @ExampleObject(name = "A Stock with no shoe",
                                    value = "{\n" +
                                            "    \"state\": \"EMPTY\",\n" +
                                            "    \"shoes\": []\n" +
                                            "}")})})
    @Parameter(name = "version", in = ParameterIn.HEADER, description = "Backend implementation version", example = "2")
    @GetMapping
    public Stock get(@RequestHeader Integer version) {
        return stockFacade.get(version).get();
    }

    @Operation(summary = "Update the stock")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The patch operation to update/modify shoes of the store, following this specification http://jsonpatch.com/",
            content = {@Content(examples = {
                    @ExampleObject(name = "1.Add some shoes to the store",
                            value = "[\n" +
                                    "    {\n" +
                                    "        \"op\": \"add\",\n" +
                                    "        \"path\": \"/shoes\",\n" +
                                    "        \"value\": [\n" +
                                    "            {\n" +
                                    "                \"color\": \"BLACK\",\n" +
                                    "                \"size\": 40,\n" +
                                    "                \"quantity\": 10\n" +
                                    "            },\n" +
                                    "            {\n" +
                                    "                \"color\": \"BLACK\",\n" +
                                    "                \"size\": 41,\n" +
                                    "                \"quantity\": 0\n" +
                                    "            },\n" +
                                    "            {\n" +
                                    "                \"color\": \"BLUE\",\n" +
                                    "                \"size\": 39,\n" +
                                    "                \"quantity\": 10\n" +
                                    "            }\n" +
                                    "        ]\n" +
                                    "    }\n" +
                                    "]"),
                    @ExampleObject(name = "2.Add one new shoe to the store at the end of the list",
                            value = "[\n" +
                                    "    {\n" +
                                    "        \"op\": \"add\",\n" +
                                    "        \"path\": \"/shoes/-\",\n" +
                                    "        \"value\": {\n" +
                                    "            \"color\": \"BLUE\",\n" +
                                    "            \"size\": 46,\n" +
                                    "            \"quantity\": 2\n" +
                                    "        }\n" +
                                    "    }\n" +
                                    "]"),
                    @ExampleObject(name = "3.Modify the quantity of the shoe at the end of the list",
                            value = "[\n" +
                                    "    {\n" +
                                    "        \"op\": \"replace\",\n" +
                                    "        \"path\": \"/shoes/3/quantity\",\n" +
                                    "        \"value\": \"300\"\n" +
                                    "    }\n" +
                                    "]"),
                    @ExampleObject(name = "4.Remove the first shoe from the store",
                            value = "[\n" +
                                    "    {\n" +
                                    "        \"op\": \"remove\",\n" +
                                    "        \"path\": \"/shoes/0\"\n" +
                                    "    }\n" +
                                    "]")})})
    @Parameter(name = "version", in = ParameterIn.HEADER, description = "Backend implementation version", example = "2")
    @PatchMapping
    public Stock patch(@RequestHeader Integer version,
                       @RequestBody JsonPatch patch) {
        try {
            return stockFacade.get(version).patch(patch);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
