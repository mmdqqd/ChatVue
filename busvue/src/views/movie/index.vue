<template>
  <div class="movie-container">
    <div class="search-bar">
      <el-input
        v-model="searchForm.movieName"
        placeholder="请输入电影名称"
        style="width: 200px"
        clearable
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button type="success" @click="handleAdd">新增</el-button>
    </div>

    <el-table :data="tableData" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="movieName" label="电影名称" />
      <el-table-column prop="movieIntroduce" label="导演" />
      <el-table-column prop="movieStar" label="评分" width="100" />
      <el-table-column prop="movieEvaluate" label="评价" show-overflow-tooltip />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        v-model:current-page="searchForm.current"
        v-model:page-size="searchForm.size"
        :page-sizes="[6, 12, 18, 24]"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
      />
    </div>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
      @close="handleDialogClose"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="电影名称" prop="movieName">
          <el-input v-model="form.movieName" />
        </el-form-item>
        <el-form-item label="导演" prop="movieIntroduce">
          <el-input v-model="form.movieIntroduce" />
        </el-form-item>
        <el-form-item label="评分" prop="movieStar">
          <el-rate
            v-model="form.movieStar"
            :max="10"
            :allow-half="true"
            show-score
          />
        </el-form-item>
        <el-form-item label="评价" prop="movieEvaluate">
          <el-input
            v-model="form.movieEvaluate"
            type="textarea"
            :rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { getMovieList, addMovie, updateMovie, deleteMovie } from '@/api/movie'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  setup() {
    const loading = ref(false)
    const tableData = ref([])
    const total = ref(0)
    const dialogVisible = ref(false)
    const dialogTitle = ref('')
    const formRef = ref()

    const searchForm = reactive({
      current: 1,
      size: 6,
      movieName: ''
    })

    const form = reactive({
      id: null,
      movieName: '',
      movieIntroduce: '',
      movieStar: 0,
      movieEvaluate: ''
    })

    const rules = {
      movieName: [{ required: true, message: '请输入电影名称', trigger: 'blur' }],
      movieIntroduce: [{ required: true, message: '请输入导演名字', trigger: 'blur' }],
      movieStar: [{ required: true, message: '请选择评分', trigger: 'change' }]
    }

    const handleSearch = async () => {
      loading.value = true
      try {
        const res = await getMovieList({
          current: searchForm.current,
          size: searchForm.size,
          movieName: searchForm.movieName
        })
        if (res.code === 200) {
          tableData.value = res.data.records
          total.value = res.data.total
        }
      } catch (error) {
        console.error(error)
        ElMessage.error('获取数据失败')
      } finally {
        loading.value = false
      }
    }

    const handleAdd = () => {
      dialogTitle.value = '新增电影'
      dialogVisible.value = true
      form.id = null
      form.movieName = ''
      form.movieIntroduce = ''
      form.movieStar = 0
      form.movieEvaluate = ''
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑电影'
      dialogVisible.value = true
      Object.assign(form, row)
    }

    const handleDelete = (row) => {
      ElMessageBox.confirm('确认删除该电影信息吗？', '提示', {
        type: 'warning'
      }).then(async () => {
        const res = await deleteMovie(row.id)
        if (res.code === 200) {
          ElMessage.success('删除成功')
          handleSearch()
        }
      })
    }

    const handleSubmit = async () => {
      await formRef.value.validate()
      if (form.id) {
        const res = await updateMovie(form.id, form)
        if (res.code === 200) {
          ElMessage.success('更新成功')
          dialogVisible.value = false
          handleSearch()
        }
      } else {
        const res = await addMovie(form)
        if (res.code === 200) {
          ElMessage.success('添加成功')
          dialogVisible.value = false
          handleSearch()
        }
      }
    }

    const handleDialogClose = () => {
      formRef.value?.resetFields()
    }

    const handleCurrentChange = (val) => {
      searchForm.current = val
      handleSearch()
    }

    const handleSizeChange = (val) => {
      searchForm.size = val
      searchForm.current = 1
      handleSearch()
    }

    onMounted(() => {
      handleSearch()
    })

    return {
      loading,
      tableData,
      total,
      dialogVisible,
      dialogTitle,
      formRef,
      searchForm,
      form,
      rules,
      handleSearch,
      handleAdd,
      handleEdit,
      handleDelete,
      handleSubmit,
      handleDialogClose,
      handleCurrentChange,
      handleSizeChange
    }
  }
}
</script>

<style scoped>
.movie-container {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style> 