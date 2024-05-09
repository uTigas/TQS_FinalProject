package tqsgroup.chuchu.admin.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStationRequest {
    private String name;
    private int numberOfLines;
}
