docker stop $(docker ps -aq)
docker container stop hcp-postgres
docker container rm hcp-postgres
docker container run --name hcp-postgres -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e DB_NAME=postgres -v /Users/ashutosh/docker/hcp/postgres:/var/lib/postgresql/data postgres:13.5
