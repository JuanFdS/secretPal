'use strict';

angular.module('secretPalApp').factory('Worker', function () {

  /**
   * Constructor, with class name
   */
  function User(firstName, lastName, mail, birthdate) {
    // Public properties, assigned to the instance ('this')
    this.firstName = firstName;
    this.lastName = lastName;
    this.mail = mail;
    this.birthdate = birthdate;
  }

  /**
   * Public method, assigned to prototype
   */
  Worker.prototype.getFullName = function () {
    return this.firstName + ' ' + this.lastName;
  };

  /**
   * Static method, assigned to class
   * Instance ('this') is not available in static context
   */
  Worker.build = function (data) {
    return new User(
      data.first_name,
      data.last_name,
      data.mail,
      data.birthdate
    );
  };

  return Worker;
});


