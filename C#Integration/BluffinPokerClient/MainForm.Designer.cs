namespace BluffinPokerClient
{
    partial class MainForm
    {
        /// <summary>
        /// Variable nécessaire au concepteur.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Nettoyage des ressources utilisées.
        /// </summary>
        /// <param name="disposing">true si les ressources managées doivent être supprimées ; sinon, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Code généré par le Concepteur Windows Form

        /// <summary>
        /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas
        /// le contenu de cette méthode avec l'éditeur de code.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.lblStatus = new System.Windows.Forms.ToolStripStatusLabel();
            this.lblTitle = new System.Windows.Forms.Label();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.btnConnect = new System.Windows.Forms.ToolStripButton();
            this.btnRefresh = new System.Windows.Forms.ToolStripButton();
            this.btnAddTable = new System.Windows.Forms.ToolStripButton();
            this.btnJoinTable = new System.Windows.Forms.ToolStripButton();
            this.btnLeaveTable = new System.Windows.Forms.ToolStripButton();
            this.datTables = new System.Windows.Forms.DataGridView();
            this.ID = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.PokerTableName = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.GameType = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.BigBlind = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.NbPlayers = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.statusStrip1.SuspendLayout();
            this.toolStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.datTables)).BeginInit();
            this.SuspendLayout();
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.lblStatus});
            this.statusStrip1.Location = new System.Drawing.Point(0, 225);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(500, 22);
            this.statusStrip1.TabIndex = 0;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // lblStatus
            // 
            this.lblStatus.Name = "lblStatus";
            this.lblStatus.Size = new System.Drawing.Size(88, 17);
            this.lblStatus.Text = "Not Connected";
            // 
            // lblTitle
            // 
            this.lblTitle.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitle.Location = new System.Drawing.Point(0, 0);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(500, 45);
            this.lblTitle.TabIndex = 1;
            this.lblTitle.Text = "Poker Client C# Lobby 1.0";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // toolStrip1
            // 
            this.toolStrip1.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.btnConnect,
            this.btnRefresh,
            this.btnAddTable,
            this.btnJoinTable,
            this.btnLeaveTable});
            this.toolStrip1.Location = new System.Drawing.Point(0, 45);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(500, 25);
            this.toolStrip1.TabIndex = 2;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // btnConnect
            // 
            this.btnConnect.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnConnect.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnConnect.Name = "btnConnect";
            this.btnConnect.Size = new System.Drawing.Size(56, 22);
            this.btnConnect.Text = "Connect";
            this.btnConnect.Click += new System.EventHandler(this.btnConnect_Click);
            // 
            // btnRefresh
            // 
            this.btnRefresh.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnRefresh.Enabled = false;
            this.btnRefresh.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.Size = new System.Drawing.Size(50, 22);
            this.btnRefresh.Text = "Refresh";
            this.btnRefresh.Click += new System.EventHandler(this.btnRefresh_Click);
            // 
            // btnAddTable
            // 
            this.btnAddTable.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnAddTable.Enabled = false;
            this.btnAddTable.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnAddTable.Name = "btnAddTable";
            this.btnAddTable.Size = new System.Drawing.Size(65, 22);
            this.btnAddTable.Text = "Add Table";
            this.btnAddTable.Click += new System.EventHandler(this.btnAddTable_Click);
            // 
            // btnJoinTable
            // 
            this.btnJoinTable.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnJoinTable.Enabled = false;
            this.btnJoinTable.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnJoinTable.Name = "btnJoinTable";
            this.btnJoinTable.Size = new System.Drawing.Size(64, 22);
            this.btnJoinTable.Text = "Join Table";
            this.btnJoinTable.Click += new System.EventHandler(this.btnJoinTable_Click);
            // 
            // btnLeaveTable
            // 
            this.btnLeaveTable.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnLeaveTable.Enabled = false;
            this.btnLeaveTable.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnLeaveTable.Name = "btnLeaveTable";
            this.btnLeaveTable.Size = new System.Drawing.Size(73, 22);
            this.btnLeaveTable.Text = "Leave Table";
            this.btnLeaveTable.Click += new System.EventHandler(this.btnLeaveTable_Click);
            // 
            // datTables
            // 
            this.datTables.AllowUserToAddRows = false;
            this.datTables.AllowUserToDeleteRows = false;
            this.datTables.BackgroundColor = System.Drawing.SystemColors.Control;
            this.datTables.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.datTables.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.ID,
            this.PokerTableName,
            this.GameType,
            this.BigBlind,
            this.NbPlayers});
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.datTables.DefaultCellStyle = dataGridViewCellStyle1;
            this.datTables.Dock = System.Windows.Forms.DockStyle.Fill;
            this.datTables.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.datTables.Location = new System.Drawing.Point(0, 70);
            this.datTables.MultiSelect = false;
            this.datTables.Name = "datTables";
            this.datTables.ReadOnly = true;
            this.datTables.RowHeadersVisible = false;
            this.datTables.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.datTables.ShowEditingIcon = false;
            this.datTables.Size = new System.Drawing.Size(500, 155);
            this.datTables.TabIndex = 3;
            this.datTables.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.datTables_CellDoubleClick);
            this.datTables.SelectionChanged += new System.EventHandler(this.datTables_SelectionChanged);
            this.datTables.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.datTables_CellContentClick);
            // 
            // ID
            // 
            this.ID.HeaderText = "ID";
            this.ID.Name = "ID";
            this.ID.ReadOnly = true;
            // 
            // PokerTableName
            // 
            this.PokerTableName.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.PokerTableName.HeaderText = "Name";
            this.PokerTableName.Name = "PokerTableName";
            this.PokerTableName.ReadOnly = true;
            // 
            // GameType
            // 
            this.GameType.HeaderText = "Game Type";
            this.GameType.Name = "GameType";
            this.GameType.ReadOnly = true;
            // 
            // BigBlind
            // 
            this.BigBlind.HeaderText = "Big Blind";
            this.BigBlind.Name = "BigBlind";
            this.BigBlind.ReadOnly = true;
            // 
            // NbPlayers
            // 
            this.NbPlayers.HeaderText = "Nb. Players";
            this.NbPlayers.Name = "NbPlayers";
            this.NbPlayers.ReadOnly = true;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(500, 247);
            this.Controls.Add(this.datTables);
            this.Controls.Add(this.toolStrip1);
            this.Controls.Add(this.lblTitle);
            this.Controls.Add(this.statusStrip1);
            this.Name = "MainForm";
            this.Text = "Poker Client C# Lobby 1.0";
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.datTables)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel lblStatus;
        private System.Windows.Forms.Label lblTitle;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton btnConnect;
        private System.Windows.Forms.DataGridView datTables;
        private System.Windows.Forms.ToolStripButton btnRefresh;
        private System.Windows.Forms.ToolStripButton btnAddTable;
        private System.Windows.Forms.ToolStripButton btnJoinTable;
        private System.Windows.Forms.ToolStripButton btnLeaveTable;
        private System.Windows.Forms.DataGridViewTextBoxColumn ID;
        private System.Windows.Forms.DataGridViewTextBoxColumn PokerTableName;
        private System.Windows.Forms.DataGridViewTextBoxColumn GameType;
        private System.Windows.Forms.DataGridViewTextBoxColumn BigBlind;
        private System.Windows.Forms.DataGridViewTextBoxColumn NbPlayers;
    }
}

