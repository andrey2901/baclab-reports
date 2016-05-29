function incude(name) {
	var link = document.querySelector('link[rel="import"][href="' + name
			+ '.html"]');
	var content = link.import.querySelector('#' + name);
	document.body.querySelector('#wrapper')
			.appendChild(content.cloneNode(true));
}