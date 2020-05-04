
CREATE (wblake:Artist {name:'wblake', fullname:'William Blake', born:1757, birthplace: 'London'})
,(rblake:Artist {name:'rblake', fullname:'Robert Blake', born:1762, birthplace: 'Edinburgh'})
, (ecoley:Artist {name:'ecoley', fullname:'Sir Edward Coley Burne-Jones, Bt', born:1833, birthplace: 'Birmingham'})
, (wcallow:Artist {name:'wcallow', fullname:'William Callow', born:1812, birthplace: 'Greenwich'})
, (ecalvert:Artist {name:'ecalvert', fullname:'Edward Calvert', born:1799, birthplace: 'London'})
, (rab:Subject {name:'rab',fullname:'religion and belief'})
, (arch:Subject {name:'arch',fullname:'architecture'})
, (laf:Subject {name:'laf',fullname:'literature and fiction'})
, (land:Subject {name:'land',fullname:'landscape'})
, (river:Subject {name:'river',fullname:'river'})
, (society:Subject {name:'society',fullname:'society'})
, (armsr:Subject {name:'armsr',fullname:'arms raised'})
, (boat:Subject {name:'boat',fullname:'boat'})
, (seamon:Subject {name:'seamon',fullname:'sea monsters'})
, (royalty:Subject {name:'royalty',fullname:'royalty'})
, (leop:Medium {name:'leop',fullname:'Line engraving on paper'})
, (gop:Medium {name:'gop',fullname:'Graphite on paper'})
, (cop:Medium {name:'cop',fullname:'Chalk on paper'})
, (wop:Medium {name:'wop',fullname:'Watercolour on paper'})
,  (ittboj:Artwork {name:'ittboj',fullname:"Illustrations to 'The Book of Job'"})
, (itddc:Artwork {name:'itddc',fullname:"Illustrations to Dante's 'Divine Comedy'"})
, (thecru:Artwork {name:'thecru',fullname:"The Crucifixion"})
, (sdofwoa:Artwork {name:'sdofwoa',fullname:"Six Drawings of Figures with Outstretched Arms"})
, (tpow:Artwork {name:'tpow',fullname:"The Preaching of Warning"})
, (tdoff:Artwork {name:'tdoff',fullname:"Two Drawings of Frightened Figures"})
, (sothot:Artwork {name:'sothot',fullname:"Study of the Head of Tristram"})
, (sosf:Artwork {name:'sosf',fullname:"Study of Seated Figure"})
, (stboni:Artwork {name:'stboni',fullname:"St Boniface"})
, (namur:Artwork {name:'namur',fullname:"Namur"})
, (rotterdam:Artwork {name:'rotterdam',fullname:"Rotterdam"})
, (pharailde:Artwork {name:'pharailde',fullname:"Place St Pharailde"})
, (flood:Artwork {name:'flood',fullname:"The Flood"})
, (pastoral:Artwork {name:'pastoral',fullname:"Ideal Pastoral Life"})
, (bacchante:Artwork {name:'bacchante',fullname:"The Bacchante"})
, (ittboj)-[:CREATED_BY]->(wblake),
  (ittboj)-[:CREATED_BY_MEDIUM]->(leop),
  (ittboj)-[:CONTAINS_SUBJECT]->(rab),
  (ittboj)-[:CONTAINS_SUBJECT]->(arch),
	(itddc)-[:CREATED_BY]->(wblake),
  (itddc)-[:CREATED_BY_MEDIUM]->(leop),
  (itddc)-[:CONTAINS_SUBJECT]->(laf),
  (itddc)-[:CONTAINS_SUBJECT]->(land),
  (itddc)-[:CONTAINS_SUBJECT]->(river),
(thecru)-[:CREATED_BY]->(wblake),
  (thecru)-[:CREATED_BY_MEDIUM]->(gop),
  (thecru)-[:CONTAINS_SUBJECT]->(armsr),
  (thecru)-[:CONTAINS_SUBJECT]->(society),
  (thecru)-[:CONTAINS_SUBJECT]->(rab),
(sdofwoa)-[:CREATED_BY]->(rblake),
  (sdofwoa)-[:CREATED_BY_MEDIUM]->(gop),
  (sdofwoa)-[:CONTAINS_SUBJECT]->(armsr),
  (sdofwoa)-[:CONTAINS_SUBJECT]->(boat),
  (sdofwoa)-[:CONTAINS_SUBJECT]->(rab),
  (tpow)-[:CREATED_BY]->(rblake),
  (tpow)-[:CREATED_BY_MEDIUM]->(wop),
  (tpow)-[:CONTAINS_SUBJECT]->(seamon),
  (tpow)-[:CONTAINS_SUBJECT]->(society),
  (tpow)-[:CONTAINS_SUBJECT]->(boat),
  (tdoff)-[:CREATED_BY]->(rblake),
  (tdoff)-[:CREATED_BY_MEDIUM]->(gop),
  (tdoff)-[:CONTAINS_SUBJECT]->(armsr),
  (tdoff)-[:CONTAINS_SUBJECT]->(royalty),
  (tdoff)-[:CONTAINS_SUBJECT]->(rab),
(sothot)-[:CREATED_BY]->(ecoley),
  (sothot)-[:CREATED_BY_MEDIUM]->(leop),
  (sothot)-[:CONTAINS_SUBJECT]->(river),
  (sothot)-[:CONTAINS_SUBJECT]->(boat),
  (sothot)-[:CONTAINS_SUBJECT]->(rab),
  (sosf)-[:CREATED_BY]->(ecoley),
  (sosf)-[:CREATED_BY_MEDIUM]->(wop),
  (sosf)-[:CONTAINS_SUBJECT]->(seamon),
  (sosf)-[:CONTAINS_SUBJECT]->(society),
  (sosf)-[:CONTAINS_SUBJECT]->(boat),
  (stboni)-[:CREATED_BY]->(ecoley),
  (stboni)-[:CREATED_BY_MEDIUM]->(gop),
  (stboni)-[:CONTAINS_SUBJECT]->(armsr),
  (stboni)-[:CONTAINS_SUBJECT]->(royalty),
  (stboni)-[:CONTAINS_SUBJECT]->(rab),
(namur)-[:CREATED_BY]->(wcallow),
  (namur)-[:CREATED_BY_MEDIUM]->(leop),
  (namur)-[:CONTAINS_SUBJECT]->(armsr),
  (namur)-[:CONTAINS_SUBJECT]->(rab),
  (namur)-[:CONTAINS_SUBJECT]->(royalty),
  (rotterdam)-[:CREATED_BY]->(wcallow),
  (rotterdam)-[:CREATED_BY_MEDIUM]->(wop),
  (rotterdam)-[:CONTAINS_SUBJECT]->(royalty),
  (rotterdam)-[:CONTAINS_SUBJECT]->(armsr),
  (pharailde)-[:CREATED_BY]->(wcallow),
  (pharailde)-[:CREATED_BY_MEDIUM]->(gop),
  (pharailde)-[:CONTAINS_SUBJECT]->(armsr),
  (pharailde)-[:CONTAINS_SUBJECT]->(royalty),
(flood)-[:CREATED_BY]->(ecalvert),
  (flood)-[:CREATED_BY_MEDIUM]->(cop),
  (flood)-[:CONTAINS_SUBJECT]->(seamon),
  (flood)-[:CONTAINS_SUBJECT]->(rab),
  (flood)-[:CONTAINS_SUBJECT]->(boat),
  (pastoral)-[:CREATED_BY]->(ecalvert),
  (pastoral)-[:CREATED_BY_MEDIUM]->(wop),
  (pastoral)-[:CONTAINS_SUBJECT]->(society),
  (pastoral)-[:CONTAINS_SUBJECT]->(armsr),
  (bacchante)-[:CREATED_BY]->(ecalvert),
  (bacchante)-[:CREATED_BY_MEDIUM]->(gop),
  (bacchante)-[:CONTAINS_SUBJECT]->(land),
  (bacchante)-[:CONTAINS_SUBJECT]->(society)