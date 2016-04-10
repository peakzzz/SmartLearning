package edu.sjsu.assess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/autocorrection")
public class AutoCorrectionControllerImpl implements AutoCorrectionController {


    // called when menu item 'Attempt Test' is selected. This renders
 	// viewautocorrection page.
 	@Override
 	@RequestMapping(value = "/viewProgram", method = RequestMethod.GET)
 	public String showViewAutoCorrection(Model model) {

 		return "viewautocorrection";
 	}


}
