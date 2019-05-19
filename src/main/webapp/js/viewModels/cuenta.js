/**
 * @license
 * Copyright (c) 2014, 2019, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your dashboard ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'appController', 'ojs/ojmodule-element-utils', 'ojs/ojinputtext'],
 function(oj, ko, $, app, moduleUtils) {
  
    function registerViewModel() {
      var self = this;
      
      self.logout = function(event){
    	  var p = {
    			  type : "Logout",
    			  userName : app.userName,
    			 };
    			 app.ws.send(JSON.stringify(p));

      }

      self.headerConfig = ko.observable({'view':[], 'viewModel':null});
      moduleUtils.createView({'viewPath':'views/header.html'}).then(function(view) {
        self.headerConfig({'view':view, 'viewModel':new app.getHeaderModel()})
      })

      self.connected = function() {
        // Implement if needed
      };

      self.disconnected = function() {
        // Implement if needed
      };

      self.transitionCompleted = function() {
        // Implement if needed
      };
    }

    return new registerViewModel();
  }
);
