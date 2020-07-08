package com.bozhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bozhi.bean.Area;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaMapper extends BaseMapper<Area> {
    @Select("SELECT\n" +
            "\tt2.id id,t2.area_name area_name\n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\t@r AS _id,\n" +
            "\t\t( SELECT @r := parent_id FROM area WHERE id = _id ) AS parent_id,\n" +
            "\t\t@s := @s + 1 AS sort \n" +
            "\tFROM\n" +
            "\t\t( SELECT @r := #{id}, @s := 0 ) temp,\n" +
            "\t\tarea \n" +
            "\tWHERE\n" +
            "\t\t@r > -1\n" +
            "\t) t1\n" +
            "\tJOIN area t2 ON t1._id = t2.id \n" +
            "ORDER BY\n" +
            "\tt1.parent_id\n")
    List<Area> selectFullArea(@Param(value = "id")Long id);
}
