package org.example.producermodule.dto;

import com.opencsv.bean.CsvBindByPosition;
import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "rowIndex")
@ToString
public class AnimalStreamFileDTO implements Serializable {
    @ExcelRow
    private int rowIndex;

    @ExcelCell(value = 0)
    @CsvBindByPosition(position = 0)
    private String name;

    @ExcelCell(value = 1)
    @CsvBindByPosition(position = 1)
    private String kindAnimal;

    @ExcelCell(value = 2)
    @CsvBindByPosition(position = 2)
    private String venomous;

    @ExcelCell(value = 3)
    @CsvBindByPosition(position = 3)
    private String typePowerSupply;

    @ExcelCell(value = 4)
    @CsvBindByPosition(position = 4)
    private String age;
}
