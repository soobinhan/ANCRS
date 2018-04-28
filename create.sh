#! /bin/sh
#
# create.sh
# Copyright (C) 2018 yqiu <yqiu@f24-suntzu>
#
# Distributed under terms of the MIT license.
#

mvn archetype:generate -Dappengine-version=1.9.59 -Djava8=true -DCloudSDK_Tooling=true -Dapplication-id=your-app-id -Dfilter=com.google.appengine.archetypes:
