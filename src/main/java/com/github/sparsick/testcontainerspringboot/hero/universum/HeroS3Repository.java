package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.springframework.util.SerializationUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class HeroS3Repository {

    public static final String BUCKET_NAME = "heros";
    private S3Client s3Client;

    public HeroS3Repository(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void addHero(Hero hero) {
        if (hasNotBucket(BUCKET_NAME)) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(BUCKET_NAME).build());
        }

        s3Client.putObject(PutObjectRequest.builder().bucket(BUCKET_NAME).key(hero.getName()).build(), RequestBody.fromBytes(SerializationUtils.serialize(hero)));
    }

    private boolean hasNotBucket(String bucketName) {
        ListBucketsResponse bucketList = s3Client.listBuckets(ListBucketsRequest.builder().build());
        return !bucketList.buckets().stream().map(Bucket::name).toList().contains(bucketName);
    }

    public Collection<Hero> allHeros() {
        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(BUCKET_NAME)
                .build();
        List<S3Object> s3Objects = s3Client.listObjects(listObjects).contents();

        return s3Objects.stream().map( s3Object -> {
            try {
                GetObjectRequest objectRequest = GetObjectRequest.builder().key(s3Object.key()).bucket(BUCKET_NAME).build();
                return (Hero) SerializationUtils.deserialize(s3Client.getObject(objectRequest).readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
