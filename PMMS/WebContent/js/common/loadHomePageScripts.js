require.config({
	baseUrl:'/PMMS/js'});
require(['jquery', 'ge-bootstrap', 'datagrids',
          'datatables', 'datatables-scroller'] ,
         function()
         {
                 require([ 'common/geam-JSON','common/geam-ajax' ],

                function() 
                    {
                        require(['common/geam-common',
                              'common/geam-Data-Grid'],
                                 function() {
                                      require(['applicationJs/home']);}  
                                    );
                        });
         }
);




