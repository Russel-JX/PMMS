requirejs.config({
  baseUrl: '../../js',
  paths: {
    'jquery':                                   'util/require-jquery',
	//'datepicker':                               'util/bootstrap-datepicker',
    'bootstrap':                                'util/bootstrap',
    'jquery-ui':                                'util/jquery-ui',
    'd3':                                       'util/d3.v3',
    'highstock':                                'util/highstock',
    'highcharts-more':                          'util/highcharts-more',
    'datatables':                               'util/ge.datatables',
    'prettify':                                 'util/prettify',
    'spin':                                     'util/spin',
	'charts':                                   'util/charts',
	'jqueryui-sortable-amd':                    'util/jquery-ui-1.10.2.custom',
	'custom':                                   'app/custom',
	'chosen':					                'util/chosen.jquery.min'

  },
  shim: {
    // Bootstrap
    'bootstrap-affix':                [ 'jquery' ],
    'bootstrap-alert':                [ 'jquery' ],
    'bootstrap-button':               [ 'jquery' ],
    'bootstrap-carousel':             [ 'jquery' ],
    'bootstrap-collapse':             [ 'jquery' ],
    'bootstrap-dropdown':             [ 'jquery' ],
    'bootstrap-modal':                [ 'jquery' ],
	'bootstrap-tooltip':              [ 'jquery' ],
    'util/bootstrap-popover':         [ 'jquery', 'util/bootstrap-tooltip' ],//needed. 
    'bootstrap-scrollspy':            [ 'jquery' ],
    'bootstrap-tab':                  [ 'jquery' ],
    'bootstrap-transition':           [ 'jquery' ],
    'bootstrap-typeahead':            [ 'jquery' ],
    'bootstrap-datepicker':           [ 'jquery' ],

    // Highcharts (Highstock)
    'highstock':                                ['jquery'],

    // Highcharts More (Highcharts/Highstock compatible supplement)
    // http://www.highcharts.com/component/content/article/2-news/47-ranges-polar-charts-and-gauges-released
    'highcharts-more':                          ['jquery', 'highstock'],

    // DataTables
    'datatables/jquery.dataTables':             ['jquery'],
    'datatables/ColReorder':                    ['datatables/jquery.dataTables']

    // D3
    // `exports` tells requirejs to use the global d3 object as the module value
    // it does not, however, allow you to change the name of the module to 'd9' or 'foobar'
    // `exports` can also be a function which returns a value like `return jQuery.noConflict()`
  }
});
