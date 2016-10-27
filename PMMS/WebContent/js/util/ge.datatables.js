/*jshint quotmark:false*/



define([
  /*'jquery',
  // custom floater behavior
  'ge/datatables-floater',
  'datatables/jquery.dataTables',
  'datatables/ColReorder',
  'datatables/FixedColumns',
  // custom paging behavior
  'ge/datatables-paging'*/
  
  'jquery',
  'util/datatables-floater',
  'util/jquery.dataTables',
  'util/ColReorder',
  'util/FixedColumns',
  'util/datatables-paging'
],
function($, addFloater) {
  'use strict';

  /* IIDS BASIC DATA GRID PUBLIC CLASS DEFINITION
  * ============================================ */

  var IIDSBasicDataGrid = function (element, options) {
    this.init('iidsBasicDataGrid', element, options);
  };

  IIDSBasicDataGrid.prototype = {

    constructor: IIDSBasicDataGrid,

    init: function(type, element, options) {
      this.type = type;
      this.$element = $(element);
      this.name = this.$element.data('table-name');
      this.settings = this.getOptions(options);

      if (this.settings.useFloater) {
        addFloater(this.settings);
      }

      this.createTable(options);
    },

    createTable: function(options) {
      // Init the datatable
      this.$table = this.$element.dataTable(this.settings);
	  if(options['iFixedColumn'] == true){
		  new FixedColumns( this.$table, {
			"iLeftColumns": options['iLeftColumns'],
			"iLeftWidth": options['iLeftWidth']
		  });
	  }
      // Row Selection
      //this.$table.on('click', 'tbody tr', $.proxy(this.clicked, this));

      // Search filter
      $('input:text[data-filter-table="' + this.name + '"]').on('keyup', $.proxy(this.searched, this));
    },

    getOptions: function(options) {
      options = $.extend({}, $.fn[this.type].defaults, options, this.$element.data());
      return options;
    },

    // Handle row clicks. Whenever the user
    // clicks a row dispatch a `selected` event
    // and pass along the row that was clicked and
    // a collection of all selected rows.
    clicked: function(e) {
      var self = this;
      var $row = $(e.currentTarget);
      // If TableTools is being used then get out of the way
      // since it provides its own click handlers
      if (!$.fn.DataTable.TableTools) {
        $row.toggleClass('row_selected');
      }

      // Make the dispatch async so we can be sure that plugins
      // like TableTools have had a chance to run first
      setTimeout(function() {
        var $rows = self.$table.find('.row_selected');
        self.$element.trigger('selected', [$row, $rows]);
      }, 0);
    },

    searched: function(e) {
      this.$table.fnFilter($(e.currentTarget).val());
    }
  };


 /* IIDS BASIC DATA GRID PLUGIN DEFINITION
  * ========================= */

  $.fn.iidsBasicDataGrid = function (option) {
    return this.each(function () {
      var $this = $(this),
          data = $this.data('iidsBasicDataGrid'),
          options = typeof option === 'object' && option;
      if (!data) $this.data('iidsBasicDataGrid', (data = new IIDSBasicDataGrid(this, options)));
      if (typeof option === 'string') data[option]();
    });
  };

  $.fn.iidsBasicDataGrid.Constructor = IIDSBasicDataGrid;

  $.fn.iidsBasicDataGrid.defaults = {
    'useFloater': true,
    'bScrollCollapse': true,
    'oLanguage': {
      'sInfo': '<strong>_START_</strong> - <strong>_END_</strong> of <strong>_TOTAL_</strong>',
      'sInfoEmpty': '<strong>0</strong> - <strong>0</strong> of <strong>0</strong>'
    },
    // bootstrap pagination from http://datatables.net/media/blog/bootstrap_2/DT_bootstrap.js
    'sDom': "TRrt<'table-controls'<'pull-left'l><'pull-right'ip>>",
    'sPaginationType': 'bootstrap'
  };
});
