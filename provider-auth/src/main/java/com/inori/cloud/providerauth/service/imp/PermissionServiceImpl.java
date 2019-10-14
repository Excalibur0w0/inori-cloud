package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.dao.mapper.TblPermissionMapper;
import com.inori.cloud.providerauth.pojo.TblPermission;
import com.inori.cloud.providerauth.pojo.TblPermissionExample;
import com.inori.cloud.providerauth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private TblPermissionMapper tblPermissionMapper;

    @Override
    public boolean addPermission(TblPermission permission) {

        return tblPermissionMapper.insert(permission) > 0;
    }

    @Override
    public boolean delPermission(String permissionId) {
        return tblPermissionMapper.deleteByPrimaryKey(permissionId) > 0;
    }

    @Override
    public boolean updatePermission(TblPermission permission, String permissionId) {
        TblPermissionExample example = new TblPermissionExample();

        example.createCriteria()
                .andUuidEqualTo(permissionId);

        return tblPermissionMapper.updateByExample(permission, example) > 0;
    }

    @Override
    public List<TblPermission> getAllPermissions() {
        return tblPermissionMapper.selectByExample(new TblPermissionExample());
    }

    @Override
    public TblPermission getById(String permission_id) {
        return tblPermissionMapper.selectByPrimaryKey(permission_id);
    }

    @Override
    public TblPermission getByPermissionCode(String permission_code) {
        TblPermissionExample example = new TblPermissionExample();
        example.createCriteria().andPermissionCodeEqualTo(permission_code);
        List<TblPermission> result = tblPermissionMapper.selectByExample(example);

        if (result != null && result.size() >= 1) {
            return result.get(0);
        } else {
            return null;
        }

    }
}
