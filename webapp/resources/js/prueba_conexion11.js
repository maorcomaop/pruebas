 
        Offline.options = { checkOnLoad: true,
                    game: false,
                    reconnect: {initialDelay:3,delay:5},
                    requests: true
                    };

            var run = function(){            
                if (Offline.state === 'up')
                {
                    Offline.check();
                    console.log ("--> "+Offline.state);
                    Offline.on('up', function() {console.log('up');});
                }
                else{
                    console.log ("--> "+Offline.state);
                    Offline.on('down', function() {console.log('down');});            
                }
        };
        setInterval(run, 5000);       