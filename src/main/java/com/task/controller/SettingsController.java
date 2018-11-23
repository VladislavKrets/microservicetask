package com.task.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.model.DAO;
import com.task.model.entities.Settings;
import com.task.model.json.SettingsSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class SettingsController
{

    @PostMapping(path = "/settings/add", consumes = "application/json")
    public String addSetting(@RequestBody String json, Model model) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Settings.class, new SettingsSerializer());
        Gson gson = builder.create();
        try {
            Settings settings = gson.fromJson(json, Settings.class);
            DAO.getInstance().addSettings(settings);
            model.addAttribute("answer", "OK");
        }
        catch (Exception e) {
            System.out.println("Json parsing exception");
            System.out.println(json);
            model.addAttribute("answer", "Incorrect json");
        }

        return "AnswerPage";
    }

    @PostMapping(path = "/settings/update", consumes = "application/json")
    public String updateSetting(@RequestBody String json, Model model) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Settings.class, new SettingsSerializer());
        Gson gson = builder.create();
        try {
            Settings settings = gson.fromJson(json, Settings.class);
            DAO.getInstance().updateSettingsByItem1(settings); //updating by item1 because it is only numeric value, id is not available in example

            model.addAttribute("answer", "OK");
        }
        catch (Exception e) { //if json is bad handle it
            System.out.println("Json parsing exception");
            System.out.println(json);
            model.addAttribute("answer", "Incorrect json");
        }

        return "AnswerPage";
    }

    @PostMapping(path = "/settings/delete", consumes = "application/json")
    public String deleteSetting(@RequestBody String json, Model model) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Settings.class, new SettingsSerializer());
        Gson gson = builder.create();
        try {
            Settings settings = gson.fromJson(json, Settings.class);
            DAO.getInstance().deleteSettingByItem1(settings); //id isn't in request
            model.addAttribute("answer", "OK");
        }
        catch (Exception e) { //if json is bad handle it
            System.out.println("Json parsing exception");
            System.out.println(json);
            model.addAttribute("answer", "Incorrect json");
        }
        return "AnswerPage";
    }

    @GetMapping(path = "/settings", produces = "application/json")
    public String getSettingsList(Model model){
        List<Settings> settings = DAO.getInstance().getAllSettings();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Settings.class, new SettingsSerializer());
        Gson gson = builder.create();
        String answer = gson.toJson(settings);
        model.addAttribute("answer", answer);
        return "AnswerPage";
    }

    @GetMapping(path = "/settings/{id}", produces = "application/json")
    public String getSetting(@PathVariable String id, Model model){
        DAO dao = DAO.getInstance();
        if (id.matches("\\d+")) {
            Settings settings = DAO.getInstance().getSettingById(Integer.parseInt(id));
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Settings.class, new SettingsSerializer());
            Gson gson = builder.create();
            String answer = gson.toJson(settings);
            model.addAttribute("answer", answer);
        }
        else model.addAttribute("answer", "Wrong request");
        return "AnswerPage";
    }



}
