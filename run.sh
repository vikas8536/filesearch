DEFAULT_UPLOADED_FILES="$(pwd)"/uploadedFiles
UPLOADED_FILES="${DATA_PATH:-$DEFAULT_UPLOADED_FILES}"
docker run -p 127.0.0.1:9090:9090/tcp \
       --mount type=bind,source="$UPLOADED_FILES",target=/var/rokt/filesearch/upload \
       filesearch