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

      self.listWordsUser=app.boardUserWords+' ';
      console.log("Lista Palabras:"+ self.listWordsUser)
      listWordsSplit=self.listWordsUser.split(",");

    self.button1Text=listWordsSplit[0];
    self.button2Text=listWordsSplit[1];
    self.button3Text=listWordsSplit[2];
    self.button4Text=listWordsSplit[3];
    self.button5Text=listWordsSplit[4];
    self.button6Text=listWordsSplit[5];
    self.button7Text=listWordsSplit[6];
    self.button8Text=listWordsSplit[7];
    self.button9Text=listWordsSplit[8];


      self.button1Click = function(){
        console.log(self.button1Text+" clicked");
      }

      self.button2Click = function(){
    	 console.log(self.button2Text+" clicked");
      }
      self.button3Click = function(){
    	  console.log(self.button3Text+" clicked");
      }
      self.button4Click = function(){
    	  console.log(self.button4Text+" clicked");
      }
      self.button5Click = function(){
    	  console.log(self.button5Text+" clicked");
      }
      self.button6Click = function(){
    	  console.log(self.button6Text+" clicked");
      }
      self.button7Click = function(){
    	  console.log(self.button7Text+" clicked");
      }
      self.button8Click = function(){
    	  console.log(self.button8Text+" clicked");
      }
      self.button9Click = function(){
    	  console.log(self.button9Text+" clicked");
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
  });
