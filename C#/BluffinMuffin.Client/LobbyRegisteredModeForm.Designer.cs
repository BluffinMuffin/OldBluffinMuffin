using System.Windows.Forms;
using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Client
{
    partial class LobbyRegisteredModeForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(LobbyRegisteredModeForm));
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.btnLeaveTable = new Button();
            this.btnJoinTable = new Button();
            this.btnAddTable = new Button();
            this.btnRefresh = new Button();
            this.btnLogOut = new Button();
            this.lblServer = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.lblMoney = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.lblAccount = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.lblTitle = new System.Windows.Forms.Label();
            this.tableList = new BluffinMuffin.Poker.Windows.Forms.Lobby.PokerTableList();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
            this.splitContainer1.IsSplitterFixed = true;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.BackColor = System.Drawing.Color.White;
            this.splitContainer1.Panel1.Controls.Add(this.pictureBox2);
            this.splitContainer1.Panel1.Controls.Add(this.pictureBox1);
            this.splitContainer1.Panel1.Controls.Add(this.btnLeaveTable);
            this.splitContainer1.Panel1.Controls.Add(this.btnJoinTable);
            this.splitContainer1.Panel1.Controls.Add(this.btnAddTable);
            this.splitContainer1.Panel1.Controls.Add(this.btnRefresh);
            this.splitContainer1.Panel1.Controls.Add(this.btnLogOut);
            this.splitContainer1.Panel1.Controls.Add(this.lblServer);
            this.splitContainer1.Panel1.Controls.Add(this.label5);
            this.splitContainer1.Panel1.Controls.Add(this.lblMoney);
            this.splitContainer1.Panel1.Controls.Add(this.label3);
            this.splitContainer1.Panel1.Controls.Add(this.lblAccount);
            this.splitContainer1.Panel1.Controls.Add(this.label1);
            this.splitContainer1.Panel1.Controls.Add(this.lblTitle);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.BackColor = System.Drawing.SystemColors.Control;
            this.splitContainer1.Panel2.Controls.Add(this.tableList);
            this.splitContainer1.Size = new System.Drawing.Size(477, 292);
            this.splitContainer1.SplitterDistance = 160;
            this.splitContainer1.SplitterWidth = 1;
            this.splitContainer1.TabIndex = 0;
            // 
            // pictureBox2
            // 
            this.pictureBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.pictureBox2.Image = global::BluffinMuffin.Client.Properties.Resources.cards;
            this.pictureBox2.Location = new System.Drawing.Point(430, 3);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(35, 35);
            this.pictureBox2.TabIndex = 15;
            this.pictureBox2.TabStop = false;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = global::BluffinMuffin.Client.Properties.Resources.cards;
            this.pictureBox1.Location = new System.Drawing.Point(17, 3);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(35, 35);
            this.pictureBox1.TabIndex = 14;
            this.pictureBox1.TabStop = false;
            // 
            // btnLeaveTable
            // 
            this.btnLeaveTable.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnLeaveTable.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnLeaveTable.Image = global::BluffinMuffin.Client.Properties.Resources.leave;
            this.btnLeaveTable.Location = new System.Drawing.Point(357, 122);
            this.btnLeaveTable.Name = "btnLeaveTable";
            this.btnLeaveTable.Size = new System.Drawing.Size(108, 35);
            this.btnLeaveTable.TabIndex = 13;
            this.btnLeaveTable.Text = "Leave Table";
            this.btnLeaveTable.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnLeaveTable.UseVisualStyleBackColor = false;
            this.btnLeaveTable.Click += new System.EventHandler(this.btnLeaveTable_Click);
            // 
            // btnJoinTable
            // 
            this.btnJoinTable.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnJoinTable.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnJoinTable.Image = global::BluffinMuffin.Client.Properties.Resources.cards_mini;
            this.btnJoinTable.Location = new System.Drawing.Point(251, 122);
            this.btnJoinTable.Name = "btnJoinTable";
            this.btnJoinTable.Size = new System.Drawing.Size(100, 35);
            this.btnJoinTable.TabIndex = 12;
            this.btnJoinTable.Text = "Join Table";
            this.btnJoinTable.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnJoinTable.UseVisualStyleBackColor = false;
            this.btnJoinTable.Click += new System.EventHandler(this.btnJoinTable_Click);
            // 
            // btnAddTable
            // 
            this.btnAddTable.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnAddTable.Image = global::BluffinMuffin.Client.Properties.Resources.add;
            this.btnAddTable.Location = new System.Drawing.Point(121, 122);
            this.btnAddTable.Name = "btnAddTable";
            this.btnAddTable.Size = new System.Drawing.Size(100, 35);
            this.btnAddTable.TabIndex = 11;
            this.btnAddTable.Text = "Add Table";
            this.btnAddTable.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnAddTable.UseVisualStyleBackColor = false;
            this.btnAddTable.Click += new System.EventHandler(this.btnAddTable_Click);
            // 
            // btnRefresh
            // 
            this.btnRefresh.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnRefresh.Image = global::BluffinMuffin.Client.Properties.Resources.refresh;
            this.btnRefresh.Location = new System.Drawing.Point(15, 122);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.Size = new System.Drawing.Size(100, 35);
            this.btnRefresh.TabIndex = 10;
            this.btnRefresh.Text = "Refresh";
            this.btnRefresh.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnRefresh.UseVisualStyleBackColor = false;
            this.btnRefresh.Click += new System.EventHandler(this.btnRefresh_Click);
            // 
            // btnLogOut
            // 
            this.btnLogOut.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnLogOut.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnLogOut.Image = global::BluffinMuffin.Client.Properties.Resources.exit;
            this.btnLogOut.Location = new System.Drawing.Point(365, 38);
            this.btnLogOut.Name = "btnLogOut";
            this.btnLogOut.Size = new System.Drawing.Size(100, 58);
            this.btnLogOut.TabIndex = 9;
            this.btnLogOut.Text = "Log Out";
            this.btnLogOut.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnLogOut.UseVisualStyleBackColor = false;
            this.btnLogOut.Click += new System.EventHandler(this.btnLogOut_Click);
            // 
            // lblServer
            // 
            this.lblServer.AutoSize = true;
            this.lblServer.Location = new System.Drawing.Point(76, 86);
            this.lblServer.Name = "lblServer";
            this.lblServer.Size = new System.Drawing.Size(79, 13);
            this.lblServer.TabIndex = 7;
            this.lblServer.Text = "127.0.0.1:4242";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(12, 86);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(48, 13);
            this.label5.TabIndex = 6;
            this.label5.Text = "Server:";
            // 
            // lblMoney
            // 
            this.lblMoney.AutoSize = true;
            this.lblMoney.Location = new System.Drawing.Point(76, 63);
            this.lblMoney.Name = "lblMoney";
            this.lblMoney.Size = new System.Drawing.Size(31, 13);
            this.lblMoney.TabIndex = 5;
            this.lblMoney.Text = "7500";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(12, 63);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(48, 13);
            this.label3.TabIndex = 4;
            this.label3.Text = "Money:";
            // 
            // lblAccount
            // 
            this.lblAccount.AutoSize = true;
            this.lblAccount.Location = new System.Drawing.Point(76, 38);
            this.lblAccount.Name = "lblAccount";
            this.lblAccount.Size = new System.Drawing.Size(174, 13);
            this.lblAccount.TabIndex = 3;
            this.lblAccount.Text = "DisplayName ( username, e@ma.il )";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 38);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(58, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "Account:";
            // 
            // lblTitle
            // 
            this.lblTitle.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.lblTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitle.Location = new System.Drawing.Point(58, 0);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(355, 38);
            this.lblTitle.TabIndex = 1;
            this.lblTitle.Text = "Bluffin Muffin Poker Client";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tableList
            // 
            this.tableList.BackColor = System.Drawing.Color.White;
            this.tableList.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableList.LobbyType = LobbyTypeEnum.RegisteredMode;
            this.tableList.Location = new System.Drawing.Point(0, 0);
            this.tableList.Name = "tableList";
            this.tableList.ShowRegisteredMode = true;
            this.tableList.ShowQuickMode = false;
            this.tableList.Size = new System.Drawing.Size(477, 131);
            this.tableList.TabIndex = 8;
            this.tableList.OnListRefreshed += new System.EventHandler(this.tableList_OnListRefreshed);
            this.tableList.OnSelectionChanged += new System.EventHandler(this.tableList_OnSelectionChanged);
            this.tableList.OnChoiceMade += new System.EventHandler(this.tableList_OnChoiceMade);
            // 
            // LobbyRegisteredModeForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(477, 292);
            this.Controls.Add(this.splitContainer1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(493, 330);
            this.Name = "LobbyRegisteredModeForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Bluffin Muffin Poker Client";
            this.Activated += new System.EventHandler(this.LobbyRegisteredModeForm_Activated);
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.RegisteredModeLobbyForm_FormClosed);
            this.Load += new System.EventHandler(this.LobbyRegisteredModeForm_Load);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.Label lblTitle;
        private System.Windows.Forms.Label lblServer;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label lblMoney;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label lblAccount;
        private System.Windows.Forms.Label label1;
        private BluffinMuffin.Poker.Windows.Forms.Lobby.PokerTableList tableList;
        private Button btnLogOut;
        private Button btnRefresh;
        private Button btnAddTable;
        private Button btnLeaveTable;
        private Button btnJoinTable;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.PictureBox pictureBox1;
    }
}