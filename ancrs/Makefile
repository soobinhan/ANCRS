#
# Makefile
# yqiu, 2018-04-28 11:07
#

projectId = ancrs-202523

run:
	mvn -Plocal clean jetty:run-exploded -DprojectID=$(projectId)

deploy:
	mvn appengine:deploy -DprojectID=$(projectId)

browse:
	gcloud app browse --project=$(projectId)

# vim:ft=make
#
