package com.d.web;

import com.d.config.FormModelResolver;
import com.d.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class CatController {
    @PostMapping(path = "/cat/save")
    public Object save(@FormModelResolver.FormModel Cat cat) {
        return Result.success(cat);
    }

    @PostMapping(path = "/cat/xml", produces = "application/xml; charset=UTF-8")
    public Object xml(@RequestBody LinkedHashMap<String, String> map) {
        return Result.success(map);
    }

    public static class Cat {
        private int catId;
        private String[] name;
        private List<String> codes;
        private List<Area> areas;

        public int getCatId() {
            return catId;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public String[] getName() {
            return name;
        }

        public void setName(String[] name) {
            this.name = name;
        }

        public List<String> getCodes() {
            return codes;
        }

        public void setCodes(List<String> codes) {
            this.codes = codes;
        }

        public List<Area> getAreas() {
            return areas;
        }

        public void setAreas(List<Area> areas) {
            this.areas = areas;
        }

        public static class Area {
            private int areaId;
            private String areaName;

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }
        }
    }
}
