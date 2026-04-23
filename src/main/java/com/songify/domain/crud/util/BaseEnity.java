package com.songify.domain.crud.util;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEnity implements Serializable {

    public  UUID uuid =  UUID.randomUUID();

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        BaseEnity that = (BaseEnity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {return Objects.hash(uuid);}

    @CreationTimestamp
    public Instant createdOn;

    @Version
    public long version;
}
