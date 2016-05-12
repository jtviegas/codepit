#!/bin/sh

#create cluster
#curl -X POST -u "91a9aa7a-d710-42cf-964d-0a576e01a017":"tFQspZXKOJP0" "https://gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters" -d "" 

#check cluster status
#curl -u "91a9aa7a-d710-42cf-964d-0a576e01a017":"tFQspZXKOJP0" \
#"https://gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters/sc1c7b558a_5077_48c5_a450_fd07fc08db04"

#add example config to cluster
#curl -X POST -H "Content-Type: application/zip" -u "91a9aa7a-d710-42cf-964d-0a576e01a017":"tFQspZXKOJP0" \
#	"https://gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters/sc1c7b558a_5077_48c5_a450_fd07fc08db04/config/example_config" \
#	--data-binary @cranfield_solr_config.zip

#create a collection
#curl -X POST -u "91a9aa7a-d710-42cf-964d-0a576e01a017":"tFQspZXKOJP0" \
#"https://gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters/sc1c7b558a_5077_48c5_a450_fd07fc08db04/solr/admin/collections" \
#-d "action=CREATE&name=example_collection&collection.configName=example_config"	

#add answers and questions to collection
#curl -X POST -H "Content-Type: application/json" -u "91a9aa7a-d710-42cf-964d-0a576e01a017":"tFQspZXKOJP0"\
# "https://gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters/sc1c7b558a_5077_48c5_a450_fd07fc08db04/solr/example_collection/update" \
# --data-binary @cranfield_data.json

#train cluster with more ground truth data
#python ./train.py -u "91a9aa7a-d710-42cf-964d-0a576e01a017":"tFQspZXKOJP0" -i cranfield_gt.csv -c sc1c7b558a_5077_48c5_a450_fd07fc08db04 -x example_collection -n "example_ranker"

# get solr query output
#https://91a9aa7a-d710-42cf-964d-0a576e01a017:tFQspZXKOJP0@gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters/sc1c7b558a_5077_48c5_a450_fd07fc08db04/solr/example_collection/select?q=what is the basic mechanism of the transonic aileron buzz&wt=json&fl=id,title

# get ranker status
#curl -u "91a9aa7a-d710-42cf-964d-0a576e01a017":"tFQspZXKOJP0" "https://gateway.watsonplatform.net/retrieve-and-rank/api/v1/rankers/3b140ax14-rank-1711"

# get ranker query output
#https://91a9aa7a-d710-42cf-964d-0a576e01a017:tFQspZXKOJP0@gateway.watsonplatform.net/retrieve-and-rank/api/v1/solr_clusters/sc1c7b558a_5077_48c5_a450_fd07fc08db04/solr/example_collection/fcselect?ranker_id=3b140ax14-rank-1711&q=what is the basic mechanism of the transonic aileron buzz&wt=json&fl=id,title