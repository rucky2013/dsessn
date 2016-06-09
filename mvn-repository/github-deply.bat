del com/

cd ../dsessn/
call mvn -DaltDeploymentRepository=snapshot-repo::default::file:../mvn-repository clean deploy

cd ../dsessn-jetty/
call mvn -DaltDeploymentRepository=snapshot-repo::default::file:../mvn-repository clean deploy

cd ../dsessn-tomcat/
call mvn -DaltDeploymentRepository=snapshot-repo::default::file:../mvn-repository clean deploy

@pause
