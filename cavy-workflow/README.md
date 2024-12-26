表结构
主要表前缀及其用途：

ACT_RE_*：RE 代表 repository（存储）。这些表包含静态信息，如流程定义和流程的资源（图片、规则等）。RepositoryService 接口操作的表。
ACT_RU_*：RU 代表 runtime。这些表存储运行时信息，如流程实例、用户任务、变量、作业等。Flowable 只在流程实例运行中保存运行时数据，并在流程实例结束时删除记录，以保证运行时表小且快。RuntimeService 接口操作的表。
ACT_HI_*：HI 代表 history。这些表存储历史数据，如已完成的流程实例、变量、任务等。HistoryService 接口操作的表。
ACT_ID_*：ID 表示 identity（组织机构）。这些表包含标识的信息，如用户、用户组等。IdentityService 接口操作的表。
ACT_GE_*：通用数据表，用于存储各种情况下都可能需要的数据。
核心表：

act_ge_bytearray：二进制数据表，用于存储流程定义、流程模板、流程图的图片等
act_re_deployment：记录部署操作的表，一次部署操作对应一条记录
act_re_procde：流程定义表，一次部署可以部署多个流程，一个流程对应一条记录
act_ru_task：存储运行中流程的任务节点信息，常用于查询人员或部门的待办任务。
act_ru_execution：运行时流程执行实例表，记录运行中流程运行的各个分支信息。
act_hi_procinst：历史流程实例表，存储流程实例历史数据（包含正在运行的流程实例）。