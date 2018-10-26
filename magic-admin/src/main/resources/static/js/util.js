var Util=(function(){
	return {
		parse8: function(data){
			var len=data.length;
			switch(len){
				case 1: return '0000000'+data;
				case 2: return '000000'+data;
				case 3: return '00000'+data;
				case 4: return '0000'+data;
				case 5: return '000'+data;
				case 6: return '00'+data;
				case 7: return '0'+data;
				case 8: return data;
			}
		}
	}
})();