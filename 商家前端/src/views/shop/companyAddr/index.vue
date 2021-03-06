<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="地址名称">
            <el-input v-model="form.addressName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="默认发货地址">
            <el-radio v-model="form.sendStatus" :label="1">是</el-radio>
            <el-radio v-model="form.chargeType" :label="0" style="width: 110px;">否</el-radio>
          </el-form-item>
          <el-form-item label="是否默认收货地址">
            <el-radio v-model="form.receiveStatus" :label="1">是</el-radio>
            <el-radio v-model="form.receiveStatus" :label="0" style="width: 110px;">否</el-radio>
          </el-form-item>
          <el-form-item label="收发货人姓名">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="收货人电话">
            <el-input v-model="form.phone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="省/直辖市">
            <el-input v-model="form.province" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="市">
            <el-input v-model="form.city" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="区">
            <el-input v-model="form.region" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="详细地址">
            <el-input v-model="form.detailAddress" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="编号" />
        <el-table-column v-if="columns.visible('addressName')" prop="addressName" label="地址名称" />
        <el-table-column v-if="columns.visible('sendStatus')" prop="sendStatus" label="默认发货地址" >
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.sendStatus === 0" style="cursor: pointer" :type="''">否</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'refunding' ">是</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('receiveStatus')" prop="receiveStatus" label="是否默认收货地址" >
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.receiveStatus === 0" style="cursor: pointer" :type="''">否</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'refunding' ">是</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('name')" prop="name" label="收发货人姓名" />
        <el-table-column v-if="columns.visible('phone')" prop="phone" label="收货人电话" />
        <el-table-column v-if="columns.visible('province')" prop="province" label="省/直辖市" />
        <el-table-column v-if="columns.visible('city')" prop="city" label="市" />
        <el-table-column v-if="columns.visible('region')" prop="region" label="区" />
        <el-table-column v-if="columns.visible('detailAddress')" prop="detailAddress" label="详细地址" />
        <el-table-column v-if="columns.visible('addTime')" prop="addTime" label="添加时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('modifyTime')" prop="modifyTime" label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.modifyTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','companyAddress:edit','companyAddress:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudCompanyAddress from '@/api/companyAddress'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '订单发货地址', url: 'api/companyAddress', sort: 'id,desc', crudMethod: { ...crudCompanyAddress }})
const defaultForm = { id: null, addressName: null, sendStatus: null, receiveStatus: null, name: null, phone: null, province: null, city: null, region: null, detailAddress: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'CompanyAddress',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'companyAddress:add'],
        edit: ['admin', 'companyAddress:edit'],
        del: ['admin', 'companyAddress:del']
      },
      rules: {
        addTime: [
          { required: true, message: '添加时间不能为空', trigger: 'blur' }
        ],
        modifyTime: [
          { required: true, message: '更新时间不能为空', trigger: 'blur' }
        ],
        deleted: [
          { required: true, message: '逻辑删除不能为空', trigger: 'blur' }
        ]
      }    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
