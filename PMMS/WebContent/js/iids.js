/*
This file is a good starting point when you're first developing
with the IIDS. It enables all of the bootstrap plugins, the IIDS navbar,
and jquery UI's sortable module. By no means is iids.js a required file,
it's just here so you can start coding right away. If you would like to
define your own application files which pull in pieces of the IIDS à la carte
that's strongly encouraged. For instance, if you're not using jQuery UI you can
delete it from the section below.
*/

define([
  'jquery',
  'jquery-ui',
  'bootstrap',
  'ge/iids-navbar'
], function($) {
  'use strict';

  // Kickoff jQuery UI sortable
  $('.row').sortable({
    handle:          '.module-header',
    helper:          'clone',
    items:           '.draggable',
    forceHelperSize: true,
    revert:          200,
    tolerance:       'pointer'
  });

  // Register tooltips.
  $('[rel=tooltip]').tooltip();

  // Register popovers.
  $('[rel=popover]').popover({ trigger: 'hover' });

  // Register alerts.
  // Add close events to alerts.
  $('.alert').addClass('fade').alert();
  // This is cobbled together from bootstrap dropdown and alert. It
  // is meant to just show a hidden alert based on data-target selector
  // and hide any other visible alerts. Using the 'close' button in
  // the alert will trigger the alert close behavior, which removes
  // the alert element from the page. Note that this means it can not
  // be opened again once it has been dismissed, unless reloaded.
  $('[data-toggle=alert]').on('click.alert.data-api', function() {
    // Close all alerts.
    hideAlerts();
    // Get the jQuery element.
    var $this = $(this);
    // Look for a requested target selector.
    var selector = $this.attr('data-target');
    // If no target selector, look at the href for selector.
    if (!selector) {
      selector = $this.attr('href');
      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, ''); //strip for ie7
    }
    // Get the jQuery target element.
    var $alert = $(selector);
    // If not found, look for an alert sibling.
    $alert.length || ($alert = $this.next('.alert'));
    // Toggle the alert open.
    $alert.show().addClass('in');
    // Exit and prevent default.
    return false;
  });
  // Function to toggle all alerts closed.
  function hideAlerts() {
    $('.alert.in').alert('close');
  }
});

