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
  
    function loginViewModel() {
      var self = this;

      self.userName=ko.observable("");
      self.pwd=ko.observable("");

      self.login = function(){
        var recurso="http://localhost:8080/login";
        var data = {
          userName : self.userName(),
          pwd : self.pwd(),
        };
        data = JSON.stringify(data);
        $.ajax({
          url : recurso,
          type : "POST",
          data : data,
          xhrFields:{
            withCredentials: true
          },
          headers : {
            'Content-Type' : 'application/json'
          }
        })
        .done(function( data, textStatus, jqXHR ) {
          console.log(data.type);
          if ( data.type=="OK" ) {
            app.setConectado(self.userName());
          }else{
            window.alert(data.message)
            console.log(data.message)
          }
          
      });
      }

      self.register = function(){
        app.router.go("register");
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

    return new loginViewModel();
  }
);
