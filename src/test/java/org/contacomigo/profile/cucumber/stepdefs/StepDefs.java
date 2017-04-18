package org.contacomigo.profile.cucumber.stepdefs;

import org.contacomigo.profile.ProfileApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ProfileApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
