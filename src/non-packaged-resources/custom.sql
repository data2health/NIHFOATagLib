select
	substring(link from '.*/(.*).html') as document_number,
	title,
	link,
	description,
	pub_date::date
from
	raw_rss,
	xmltable(
		'item' passing raw
		columns
			title text path 'title',
			link text path 'link',
			description text path 'description',
			pub_date text path 'pubDate'
	);
