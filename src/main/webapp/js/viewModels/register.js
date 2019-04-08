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

      self.email=ko.observable("");
      self.userName=ko.observable("");
      self.pwd1=ko.observable("");
      self.pwd2=ko.observable("");

      self.register = function(event){
        var recurso="http://localhost:8080/register";
        var data = {
          type : "Register",
          email : self.email(),
          userName : self.userName(),
          pwd1 : self.pwd1(),
          pwd2 : self.pwd2()
        };
        data = JSON.stringify(data);
        $.ajax({
          url : recurso,
          type : "POST",
          data : data,
          headers : {
            'Content-Type' : 'application/json'
          },
          success : registerOK
        });
      }

      function registerOK(){
        app.router.go("login");
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
