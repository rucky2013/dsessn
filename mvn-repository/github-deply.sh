#!/bin/bash
rm -rf com/
cd ../dsessn/
mvn -DaltDeploymentRepository=snapshot-repo::default::file:../mvn-repository clean deploy
