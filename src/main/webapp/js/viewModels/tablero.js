/**
 * @license
 * Copyright (c) 2014, 2019, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your profile ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'appController', 'ojs/ojmodule-element-utils', 'ojs/ojbutton'],
 function(oj, ko, $, app, moduleUtils) {
  
    function TableroViewModel() {
      var self = this;
     
      self.userName=ko.observable(app.userName);
      
      self.opponentUserName=ko.observable(app.opponentUserName);
      self.currentPlayerUserName=ko.observable(app.currentPlayerUserName);

      
      console.log(app.currentPlayerUserName);
      console.log(self.opponentUserName());

      

      console.log(self.userName());

      self.button1Text="Palabra 1";
      self.button2Text="Palabra 2";
      self.button3Text="Palabra 3";
      self.button4Text="Palabra 4";
      self.button5Text="Palabra 5";
      self.button6Text="Palabra 6";
      self.button7Text="Palabra 7";
      self.button8Text="Palabra 8";
      self.button9Text="Palabra 9";


      self.button1Click = function(){
        console.log("Button1 clicked");
      }

      self.button2Click = function(){
        console.log("Button2 clicked");
      }
      self.button3Click = function(){
        console.log("Button3 clicked");
      }
      self.button4Click = function(){
        console.log("Button4 clicked");
      }
      self.button5Click = function(){
        console.log("Button5 clicked");
      }
      self.button6Click = function(){
        console.log("Button6 clicked");
      }
      self.button7Click = function(){
        console.log("Button7 clicked");
      }
      self.button8Click = function(){
        console.log("Button8 clicked");
      }
      self.button9Click = function(){
        console.log("Button9 clicked");
      }







      self.dealWithMessage = function(data){
        console.log(data);
      }
          

      // Header Config
      self.headerConfig = ko.observable({'view':[], 'viewModel':null});
      moduleUtils.createView({'viewPath':'views/header.html'}).then(function(view) {
        self.headerConfig({'view':view, 'viewModel':new app.getHeaderModel()})
      })

      // Below are a set of the ViewModel methods invoked by the oj-module component.
      // Please reference the oj-module jsDoc for additional information.

      /**
       * Optional ViewModel method invoked after the View is inserted into the
       * document DOM.  The application can put logic that requires the DOM being
       * attached here. 
       * This method might be called multiple times - after the View is created 
       * and inserted into the DOM and after the View is reconnected 
       * after being disconnected.
       */
      self.connected = function() {

      };

      /**
       * Optional ViewModel method invoked after the View is disconnected from the DOM.
       */
      self.disconnected = function() {
        // Implement if needed
      };

      /**
       * Optional ViewModel method invoked after transition to the new View is complete.
       * That includes any possible animation between the old and the new View.
       */
      self.transitionCompleted = function() {
        // Implement if needed
      };
    }

    /*
     * Returns a constructor for the ViewModel so that the ViewModel is constructed
     * each time the view is displayed.  Return an instance of the ViewModel if
     * only one instance of the ViewModel is needed.
     */
    return new TableroViewModel();
  }
);
