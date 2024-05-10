package tqsgroup.chuchu.admin.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import tqsgroup.chuchu.data.entity.TrainType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainDAO {
    private TrainType type;
    private int number;
}
