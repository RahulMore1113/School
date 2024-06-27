package com.rahul.contoller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rahul.model.Holiday;
import com.rahul.repo.HolidaysRepo;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HolidaysController {

    @Autowired
    private HolidaysRepo repo;

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display, Model model) {

        boolean isAll = display.equals("all");

        Map<String, Boolean> displayMap = Map.of(
                "all", isAll,
                "federal", display.equals("federal") || isAll,
                "festival", display.equals("festival") || isAll
        );

        displayMap.forEach(model::addAttribute);

        List<Holiday> holidayList = StreamSupport
                .stream(repo.findAll().spliterator(), false)
                .toList();

        Arrays.stream(Holiday.Type.values()).forEach(type ->
                model.addAttribute(type.toString(),
                        holidayList.stream()
                                .filter(holiday -> holiday.getType().equals(type))
                                .toList())
        );

        return "holidays.html";

    }

}
